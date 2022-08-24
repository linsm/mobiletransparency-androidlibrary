package at.jku.ins.mobiletransparency.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Tree {

    @Expose
    @SerializedName("tree")
    private List<TreeInformation> treeInformation;

}
