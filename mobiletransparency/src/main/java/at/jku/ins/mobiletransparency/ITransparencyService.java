package at.jku.ins.mobiletransparency;

import at.jku.ins.mobiletransparency.models.InclusionProof;
import at.jku.ins.mobiletransparency.models.TreeInformation;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITransparencyService {

    @GET("log")
    InclusionProof getInclusionProof(@Path("treeId") int treeId, @Path("treeSize") int treeSize,
                                     @Query("api_key") String apiKey);

    @GET("admin")
    TreeInformation listTrees();

}
