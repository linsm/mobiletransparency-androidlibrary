package at.jku.ins.mobiletransparency;

import java.net.URL;

import at.jku.ins.mobiletransparency.models.InclusionProof;
import at.jku.ins.mobiletransparency.models.LogEntry;
import at.jku.ins.mobiletransparency.models.TreeInformation;
import okhttp3.HttpUrl;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MobileTransparencyClient {

    private static Retrofit retrofitClient = null;
    private ITransparencyService transparencyService;

    public MobileTransparencyClient(HttpUrl personalityAddress) {
        retrofitClient = new Retrofit.Builder()
                .baseUrl(personalityAddress)
                .addConverterFactory(GsonConverterFactory
                        .create()).build();
        transparencyService = retrofitClient.create(ITransparencyService.class);
    }

    public boolean performInclusionProofOnLatestTreeHead(LogEntry logEntry) {
        //transparencyService.getInclusionProof()
        return true;
    }

    private long getTreeId() {
        TreeInformation information = transparencyService.listTrees();
        return information.getTreeId();
    }
}