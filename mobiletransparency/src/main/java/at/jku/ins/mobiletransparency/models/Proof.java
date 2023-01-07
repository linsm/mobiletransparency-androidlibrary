// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency.models;

import com.google.gson.annotations.Expose;

import java.util.List;

public class Proof {
    @Expose
    private int leafIndex;

    @Expose
    private List<String> hashes;

    public Proof(int leafIndex, List<String> hashes) {
        this.leafIndex = leafIndex;
        this.hashes = hashes;
    }

    public int getLeafIndex() {
        return leafIndex;
    }

    public List<String> getHashes() {
        return hashes;
    }

}
