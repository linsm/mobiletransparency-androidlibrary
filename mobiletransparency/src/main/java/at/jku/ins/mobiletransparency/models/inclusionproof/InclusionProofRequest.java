// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency.models.inclusionproof;

import com.google.gson.annotations.Expose;

public class InclusionProofRequest {

    @Expose
    private long treeId;

    @Expose
    private int treeSize;

    public InclusionProofRequest(long treeId, int treeSize)
    {
        this.treeId = treeId;
        this.treeSize = treeSize;
    }

    public int getTreeSize() {
        return treeSize;
    }

    public long getTreeId() {
        return treeId;
    }
}
