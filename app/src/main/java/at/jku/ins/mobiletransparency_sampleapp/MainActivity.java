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
package at.jku.ins.mobiletransparency_sampleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import at.jku.ins.mobiletransparency.InclusionProofCallback;
import at.jku.ins.mobiletransparency.MobileTransparencyClient;
import at.jku.ins.mobiletransparency.TransparencyCallback;
import at.jku.ins.mobiletransparency.models.LogEntry;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.TreeInformation;
import at.jku.ins.mobiletransparency.models.InclusionProof;
import at.jku.ins.mobiletransparency.models.ValidationResult;
import at.jku.ins.mobiletransparency_sampleapp.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MobileTransparencyClient mobileTransparencyClient;
    private String selectedTreeId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mobileTransparencyClient = new MobileTransparencyClient("<ENTER_YOUR_PERSONALITY_ADDRESS>");
        mobileTransparencyClient.getAvailableTrees(new TransparencyCallback() {
            @Override
            public void onSuccess(Tree treeInformation) {
                List<String> treeIds = new ArrayList<>();
                for (TreeInformation item: treeInformation.getTreeInformation()) {
                    treeIds.add(String.valueOf(item.getTreeId()));
                }
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.treeid_item,treeIds);
                AutoCompleteTextView treeIdField = findViewById(R.id.treeIdAutoComplete);
                treeIdField.setAdapter(adapter);
                treeIdField.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        selectedTreeId = treeIds.get(position);
                    }
                });
            }
            @Override
            public void onFailure(String message) {
                Snackbar snackbar = Snackbar
                        .make(findViewById(R.id.treeIdAutoComplete), message, Snackbar.LENGTH_LONG);
                snackbar.show();
            }
        });
        MaterialButton inclusionProofButton = findViewById(R.id.inclusionProofButton);
        inclusionProofButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextInputLayout treeSizeTextField = findViewById(R.id.treeSize);
                int treeSize = Integer.parseInt(treeSizeTextField.getEditText().getText().toString());

                TextInputLayout applicationIdField = findViewById(R.id.applicationId);
                String applicationId = applicationIdField.getEditText().getText().toString();

                TextInputLayout versionCodeField = findViewById(R.id.versionCode);
                String versionCode = versionCodeField.getEditText().getText().toString();

                TextInputLayout signatureDataField = findViewById(R.id.signatureData);
                String signatureData = signatureDataField.getEditText().getText().toString();
                long start_time = System.currentTimeMillis();
                mobileTransparencyClient.performInclusionProofOnLatestTreeHead(Long.parseLong(selectedTreeId), treeSize, new LogEntry(applicationId, versionCode, signatureData), new InclusionProofCallback() {
                    @Override
                    public void onSuccess(ValidationResult result) {
                        long end_time = System.currentTimeMillis();
                        long elapse = end_time - start_time;
                        Snackbar snackbar = Snackbar
                                .make(v, "Expected root hash: " + result.getExpectedRootHash() + "\n Claimed root hash: " + result.getClaimedRootHash(), Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }

                    @Override
                    public void onFailure() {
                        Snackbar snackbar = Snackbar
                                .make(v, "Inclusion proof failure", Snackbar.LENGTH_LONG);
                        snackbar.show();
                    }
                });
            }
        });
    }
}