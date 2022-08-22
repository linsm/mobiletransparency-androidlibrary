package at.jku.ins.mobiletransparency.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InclusionProof {

    @Expose
    @SerializedName("treeId")
    private String treeId;

    @Expose
    @SerializedName("logRoot")
    private String logRoot;

    @Expose
    @SerializedName("leafIndex")
    private int leafIndex;

    @Expose
    @SerializedName("hashes")
    private String[] nodeHashes;
}