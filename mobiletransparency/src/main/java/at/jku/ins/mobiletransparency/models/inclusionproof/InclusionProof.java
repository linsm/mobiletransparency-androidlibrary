package at.jku.ins.mobiletransparency.models.inclusionproof;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class InclusionProof {
    @Expose
    @SerializedName("proof")
    private List<Proof> proofList;

    @Expose
    private SignedLogRoot signedLogRoot;

    public InclusionProof(List<Proof> proofList, SignedLogRoot signedLogRoot) {
        this.proofList = proofList;
        this.signedLogRoot = signedLogRoot;
    }

    public List<Proof> getProofList() {
        return proofList;
    }

    public SignedLogRoot getSignedLogRoot() {
        return signedLogRoot;
    }
}
