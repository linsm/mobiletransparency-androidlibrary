// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Handler;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import at.jku.ins.mobiletransparency.models.inclusionproof.ConsistencyProof;
import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProof;
import at.jku.ins.mobiletransparency.models.LogEntry;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.TreeInformation;
import at.jku.ins.mobiletransparency.models.inclusionproof.InclusionProofRequest;
import okhttp3.HttpUrl;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MobileTransparencyClient {

    private static Retrofit retrofitClient = null;
    private ITransparencyService remoteTransparencyService;
    private TransparencyService transparencyService;

    public MobileTransparencyClient(String personalityAddress) {
        HttpUrl url = HttpUrl.parse(personalityAddress);

        retrofitClient = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory
                        .create()).build();
        remoteTransparencyService = retrofitClient.create(ITransparencyService.class);
        transparencyService = new TransparencyService();
    }

    public void performInclusionProofOnLatestTreeHead(long treeId, int treeSize, LogEntry logEntry, InclusionProofCallback callback) {
        remoteTransparencyService.getInclusionProof(treeId, treeSize, logEntry).enqueue(new Callback<InclusionProof>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<InclusionProof> call, Response<InclusionProof> response) {
                callback.onSuccess(response.body());
                    String leafValueHash = logEntry.getMerkleLeafHash();
                transparencyService.validateInclusionProof(leafValueHash, treeSize, response.body());
            }
            @Override
            public void onFailure(Call<InclusionProof> call, Throwable t) {

            }
        });
    }

    public void performConsistencyProofOnLatestTreeHead(Context applicationContext, long treeId, ConsistencyProofCallback callback) {



        String trustedRootNode = transparencyService.getStoredRootNode(applicationContext);
        if(trustedRootNode == "") //TOFU
        {

            return;
        }


        remoteTransparencyService.getConsistencyProof(treeId, firstTreeSize, secondTreeSize).enqueue(new Callback<ConsistencyProof>() {
            @Override
            public void onResponse(Call<ConsistencyProof> call, Response<ConsistencyProof> response) {
                callback.onSuccess(response.body());

                transparencyService.validateConsistencyProof(response.body());
            }

            @Override
            public void onFailure(Call<ConsistencyProof> call, Throwable t) {

            }
        });

    }



    public void getAvailableTrees(TransparencyCallback callback) {
        remoteTransparencyService.listTrees().enqueue(new Callback<Tree>() {
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