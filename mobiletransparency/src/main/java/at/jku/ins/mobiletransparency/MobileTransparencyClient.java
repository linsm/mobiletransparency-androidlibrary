package at.jku.ins.mobiletransparency;

import android.os.Handler;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.jku.ins.mobiletransparency.models.InclusionProof;
import at.jku.ins.mobiletransparency.models.LogEntry;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.TreeInformation;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MobileTransparencyClient {

    private static Retrofit retrofitClient = null;
    private ITransparencyService transparencyService;

    public MobileTransparencyClient(String personalityAddress) {
        HttpUrl url = HttpUrl.parse(personalityAddress);

        retrofitClient = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory
                        .create()).build();
        transparencyService = retrofitClient.create(ITransparencyService.class);
    }

    public boolean performInclusionProofOnLatestTreeHead(LogEntry logEntry) {
        //transparencyService.getInclusionProof()
        return true;
    }

    public void getAvailableTrees(TransparencyCallback callback) {
        transparencyService.listTrees().enqueue(new Callback<Tree>() {
            @Override
            public void onResponse(Call<Tree> call, Response<Tree> response) {
                callback.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<Tree> call, Throwable t) {
                callback.onFailure();
            }
        });
    }
}