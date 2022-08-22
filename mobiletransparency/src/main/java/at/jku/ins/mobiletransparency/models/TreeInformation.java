package at.jku.ins.mobiletransparency.models;

import com.google.gson.annotations.Expose;

public class TreeInformation {

    @Expose
    private long treeId;

    @Expose
    private String treeState;

    public TreeInformation(long treeId, String treeState) {
        this.treeId = treeId;
        this.treeState = treeState;
    }

    public long getTreeId() {
        return treeId;
    }

    public String getTreeState() {
        return treeState;
    }
}
