package at.jku.ins.mobiletransparency.models.inclusionproof;

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
