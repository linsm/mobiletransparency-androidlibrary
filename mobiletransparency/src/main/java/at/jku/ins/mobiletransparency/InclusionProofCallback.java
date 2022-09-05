package at.jku.ins.mobiletransparency;

import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProof;

public interface InclusionProofCallback {
    void onSuccess(InclusionProof inclusionProof);
    void onFailure();
}
