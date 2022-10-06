// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency;

import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProof;

public interface InclusionProofCallback {
    void onSuccess(InclusionProof inclusionProof);
    void onFailure();
}
