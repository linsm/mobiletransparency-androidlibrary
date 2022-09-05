package at.jku.ins.mobiletransparency.models;

import static java.security.spec.MGF1ParameterSpec.SHA256;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.gson.annotations.Expose;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class LogEntry {

    @Expose
    private String applicationId;
    @Expose
    private String version;
    @Expose
    private String signatureData;

    public LogEntry(String applicationId, String version, String signatureData)
    {
        this.applicationId = applicationId;
        this.version = version;
        this.signatureData = signatureData;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public String getVersion() {
        return version;
    }

    public String getSignatureData() {
        return signatureData;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String getMerkleLeafHash() {
        byte[] prefix = { 0 };
        byte[] applicationIdBytes = applicationId.getBytes(StandardCharsets.UTF_8);
        byte[] versionBytes = version.getBytes(StandardCharsets.UTF_8);
        byte[] signatureBytes = signatureData.getBytes(StandardCharsets.UTF_8);

        byte[] result = new byte[prefix.length + applicationIdBytes.length + versionBytes.length + signatureBytes.length];
        System.arraycopy(prefix,0,result,0, prefix.length);
        System.arraycopy(applicationIdBytes,0,result,prefix.length,applicationIdBytes.length);
        System.arraycopy(versionBytes,0,result,prefix.length + applicationIdBytes.length,versionBytes.length);
        System.arraycopy(signatureBytes,0,result,prefix.length + applicationIdBytes.length + versionBytes.length,signatureBytes.length);

        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(result);
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
