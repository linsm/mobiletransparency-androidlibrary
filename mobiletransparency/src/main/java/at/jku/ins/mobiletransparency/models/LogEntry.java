package at.jku.ins.mobiletransparency.models;

import com.google.gson.annotations.Expose;

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
}
