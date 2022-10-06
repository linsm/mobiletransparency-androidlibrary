// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency;

import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import java.nio.ByteBuffer;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;

import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProof;
import at.jku.ins.mobiletransparency.models.inclusionproof.Proof;

public class TransparencyService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateInclusionProof(String merkleLeafHash, int treeSize, InclusionProof inclusionProof) {
        Proof proof = inclusionProof.getProofList().get(0);
        List<String> hashes = proof.getHashes();
        String calculatedRootHash = calculateRootNode(proof.getLeafIndex(), merkleLeafHash, treeSize, hashes);
        String claimedRootHash = getRootNodeHash(inclusionProof.getSignedLogRoot().getLogRoot());
        return claimedRootHash == calculatedRootHash;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String getRootNodeHash(String signedLogRoot) {
        byte[] rootNodeBytes = Base64.decode(signedLogRoot, Base64.DEFAULT);
        byte[] treeSizeArray = Arrays.copyOfRange(rootNodeBytes,2,10);
        Long treeSize = ByteBuffer.wrap(treeSizeArray).getLong();
        int rootHashLength = rootNodeBytes[10] & 0x0FF;
        byte[] rootHash = Arrays.copyOfRange(rootNodeBytes,11,11+rootHashLength);
        return Base64.encodeToString(rootHash,Base64.NO_WRAP);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String calculateInnerNode(String leftMerkleHash, String rightMerkleHash) {
        byte[] prefix = { 0x01 };
        byte[] rightChildBytes = Base64.decode(rightMerkleHash, Base64.DEFAULT);
        byte[] leftChildBytes =  Base64.decode(leftMerkleHash, Base64.DEFAULT);
        byte[] result = new byte[prefix.length + leftChildBytes.length + rightChildBytes.length];
        System.arraycopy(prefix,0,result,0, prefix.length);
        System.arraycopy(leftChildBytes,0,result,prefix.length, leftChildBytes.length);
        System.arraycopy(rightChildBytes,0,result, prefix.length + leftChildBytes.length,rightChildBytes.length);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hashValue = digest.digest(result);
            return Base64.encodeToString(hashValue, Base64.NO_WRAP);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateRootNode(int leafIndex, String leafHash, int treeSize, List<String> orderedHashes) {
        int currentIndex = leafIndex;
        int inner = Integer.SIZE - Integer.numberOfLeadingZeros((currentIndex ^ (treeSize-1)));

        String result = leafHash;
        String left;
        String right;

        for(int i = 0; i < orderedHashes.size(); i++) {
            if (i < inner && (((leafIndex >> i) & 1) == 0)) {
                left = result;
                right = orderedHashes.get(i);
            } else {
                left = orderedHashes.get(i);
                right = result;
            }
            result = calculateInnerNode(left, right);
        }
        return result;
    }
}
