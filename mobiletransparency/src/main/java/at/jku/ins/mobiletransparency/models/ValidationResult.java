package at.jku.ins.mobiletransparency.models;

import com.google.gson.annotations.Expose;

public class ValidationResult {
    private String expectedRootHash;
    private String claimedRootHash;

    public ValidationResult(String expectedRootHash, String claimedRootHash) {
        this.expectedRootHash = expectedRootHash;
        this.claimedRootHash = claimedRootHash;
    }

    public String getExpectedRootHash() {
        return expectedRootHash;
    }

    public String getClaimedRootHash() {
        return claimedRootHash;
    }
}
