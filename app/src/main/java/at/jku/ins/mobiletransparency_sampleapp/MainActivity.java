package at.jku.ins.mobiletransparency_sampleapp;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import at.jku.ins.mobiletransparency.MobileTransparencyClient;
import at.jku.ins.mobiletransparency.TransparencyCallback;
import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.TreeInformation;
import at.jku.ins.mobiletransparency_sampleapp.databinding.ActivityMainBinding;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private MobileTransparencyClient mobileTransparencyClient;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mobileTransparencyClient = new MobileTransparencyClient("https://10.0.2.2:8001");
        mobileTransparencyClient.getAvailableTrees(new TransparencyCallback() {
            @Override
            public void onSuccess(Tree treeInformation) {

            }

            @Override
            public void onFailure() {
                int i = 0;
            }
        });

    }
}