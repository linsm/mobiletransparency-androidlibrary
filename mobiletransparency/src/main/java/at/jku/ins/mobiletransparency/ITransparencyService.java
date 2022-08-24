package at.jku.ins.mobiletransparency;

import java.util.List;

import at.jku.ins.mobiletransparency.models.InclusionProof;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.TreeInformation;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ITransparencyService {

    @GET("log")
    Call<InclusionProof> getInclusionProof(@Path("treeId") int treeId, @Path("treeSize") int treeSize,
                                           @Query("api_key") String apiKey);

    @GET("Admin/ListTrees")
    Call<Tree> listTrees();

}
