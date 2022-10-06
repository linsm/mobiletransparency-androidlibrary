// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency;

import at.jku.ins.mobiletransparency.models.LogEntry;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProof;
import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProofRequest;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITransparencyService {

    @POST("Log/InclusionProof")
    Call<InclusionProof> getInclusionProof(@Query("treeId")long treeId, @Query("treeSize") int treeSize,
                                           @Body LogEntry logToVerify);

    @GET("Admin/ListTrees")
    Call<Tree> listTrees();

}
