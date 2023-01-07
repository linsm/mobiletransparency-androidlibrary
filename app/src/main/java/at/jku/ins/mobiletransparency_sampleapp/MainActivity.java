// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency_sampleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
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
        mobileTransparencyClient = new MobileTransparencyClient("https://192.168.56.20:8443");
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
            public void onFailure() {
                int i = 0;
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

                mobileTransparencyClient.performInclusionProofOnLatestTreeHead(Long.parseLong(selectedTreeId), treeSize, new LogEntry(applicationId, versionCode, signatureData), new InclusionProofCallback() {
                    @Override
                    public void onSuccess(InclusionProof inclusionProof) {

                    }

                    @Override
                    public void onFailure() {

                    }
                });
            }
        });
    }
}