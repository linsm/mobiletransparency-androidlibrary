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

import at.jku.ins.mobiletransparency.models.LogEntry;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.ConsistencyProof;
import at.jku.ins.mobiletransparency.models.InclusionProof;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ITransparencyService {

    @POST("Log/InclusionProof")
    Call<InclusionProof> getInclusionProof(@Query("treeId")long treeId, @Query("treeSize") int treeSize,
                                           @Body LogEntry logToVerify);

    @POST("Log/ConsistencyProof")
    Call<ConsistencyProof> getConsistencyProof(@Query("treeId")long treeId, @Query("firstTreeSize") int firstTreeSize,
                                             @Query("secondTreeSize") int secondTreeSize);

    @GET("Log/ListTrees")
    Call<Tree> listTrees();

}
