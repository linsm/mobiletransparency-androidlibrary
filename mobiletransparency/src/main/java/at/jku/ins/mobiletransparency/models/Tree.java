// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
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
