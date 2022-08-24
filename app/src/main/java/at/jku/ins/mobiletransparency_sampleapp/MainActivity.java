package at.jku.ins.mobiletransparency_sampleapp;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import at.jku.ins.mobiletransparency.MobileTransparencyClient;
import at.jku.ins.mobiletransparency.TransparencyCallback;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.TreeInformation;
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
        mobileTransparencyClient = new MobileTransparencyClient("https://10.0.2.2:8001");
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

    }
}