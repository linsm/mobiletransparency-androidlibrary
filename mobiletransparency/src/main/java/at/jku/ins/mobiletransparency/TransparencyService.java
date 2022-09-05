package at.jku.ins.mobiletransparency;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProof;
import at.jku.ins.mobiletransparency.models.inclusionproof.Proof;

public class TransparencyService {

    @RequiresApi(api = Build.VERSION_CODES.O)
    public boolean validateInclusionProof(String expectedRootHash, String merkleLeafHash, int treeSize, InclusionProof inclusionProof) {
        Proof proof = inclusionProof.getProofList().get(0);
        List<String> hashes = proof.getHashes();
        String calculatedRootHash = calculateRootNode(proof.getLeafIndex(), merkleLeafHash, treeSize, hashes);
        return expectedRootHash == calculatedRootHash;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private String calculateInnerNode(String leftMerkleHash, String rightMerkleHash) {
        byte[] prefix = { 0x01 };
        byte[] rightChildBytes = Base64.getDecoder().decode(rightMerkleHash.getBytes(StandardCharsets.UTF_8));
        byte[] leftChildBytes =  Base64.getDecoder().decode(leftMerkleHash.getBytes(StandardCharsets.UTF_8));
        byte[] result = new byte[prefix.length + leftChildBytes.length + rightChildBytes.length];
        System.arraycopy(prefix,0,result,0, prefix.length);
        System.arraycopy(leftChildBytes,0,result,prefix.length, leftChildBytes.length);
        System.arraycopy(rightChildBytes,0,result, prefix.length + leftChildBytes.length,rightChildBytes.length);
        MessageDigest digest;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hashValue = digest.digest(result);
            return Base64.getEncoder().encodeToString(hashValue);
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
