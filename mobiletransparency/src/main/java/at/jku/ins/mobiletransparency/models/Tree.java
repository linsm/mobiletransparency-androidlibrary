package at.jku.ins.mobiletransparency.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class Tree {

    @Expose
    @SerializedName("tree")
    private List<TreeInformation> treeInformation;

    Tree(List<TreeInformation> treeInformation) {
        this.treeInformation = treeInformation;
    }

    public List<TreeInformation> getTreeInformation() {
        return treeInformation;
    }

}
