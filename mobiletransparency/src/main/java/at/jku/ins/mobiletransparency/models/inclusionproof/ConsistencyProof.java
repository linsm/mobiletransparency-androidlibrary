// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency.models.inclusionproof;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConsistencyProof {
    @Expose
    @SerializedName("proof")
    private List<Proof> proofList;

    @Expose
    private SignedLogRoot signedLogRoot;

    public ConsistencyProof(List<Proof> proofList, SignedLogRoot signedLogRoot) {
        this.proofList = proofList;
        this.signedLogRoot = signedLogRoot;
    }

    public List<Proof> getProofList() {
        return proofList;
    }

    public SignedLogRoot getSignedLogRoot() {
        return signedLogRoot;
    }
}
