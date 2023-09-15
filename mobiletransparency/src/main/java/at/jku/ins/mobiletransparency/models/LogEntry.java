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
