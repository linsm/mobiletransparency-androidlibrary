// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
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
