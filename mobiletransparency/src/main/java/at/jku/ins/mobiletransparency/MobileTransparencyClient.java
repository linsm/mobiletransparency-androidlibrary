/*
** Copyright (C) 2023  Johannes Kepler University Linz, Institute of Networks and Security
** Copyright (C) 2023  CDL Digidow <https://www.digidow.eu/>
**
** Licensed under the EUPL, Version 1.2 or – as soon they will be approved by
** the European Commission - subsequent versions of the EUPL (the "Licence").
** You may not use this work except in compliance with the Licence.
** 
** You should have received a copy of the European Union Public License along
** with this program.  If not, you may obtain a copy of the Licence at:
** <https://joinup.ec.europa.eu/software/page/eupl>
** 
** Unless required by applicable law or agreed to in writing, software
** distributed under the Licence is distributed on an "AS IS" basis,
** WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
** See the Licence for the specific language governing permissions and
** limitations under the Licence.
**
*/
package at.jku.ins.mobiletransparency;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

import at.jku.ins.mobiletransparency.models.ConsistencyProof;
import at.jku.ins.mobiletransparency.models.InclusionProof;
import at.jku.ins.mobiletransparency.models.LogEntry;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.ValidationResult;
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
                String leafValueHash = logEntry.getMerkleLeafHash();
                ValidationResult result = transparencyService.validateInclusionProof(leafValueHash, treeSize, response.body());
                callback.onSuccess(result);
            }
            @Override
            public void onFailure(Call<InclusionProof> call, Throwable t) {
                int i = 0;

            }
        });
    }

    public void performConsistencyProofOnLatestTreeHead(Context applicationContext, long treeId, int firstTreeSize, int secondTreeSize, ConsistencyProofCallback callback) {
        String trustedRootNode = transparencyService.getStoredRootNode(applicationContext);
        if(trustedRootNode == "") //TOFU
        {
            return;
        }
        remoteTransparencyService.getConsistencyProof(treeId, firstTreeSize, secondTreeSize).enqueue(new Callback<ConsistencyProof>() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onResponse(Call<ConsistencyProof> call, Response<ConsistencyProof> response) {
                callback.onSuccess(response.body());
                //transparencyService.validateConsistencyProof(response.body());
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
                callback.onFailure(t.getMessage());
            }
        });
    }
}