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
