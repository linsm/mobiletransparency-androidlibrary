package at.jku.ins.mobiletransparency.models.inclusionproof;

import com.google.gson.annotations.Expose;

public class SignedLogRoot {
    @Expose
    private String logRoot;

    SignedLogRoot(String logRoot) {
        this.logRoot = logRoot;
    }

    public String getLogRoot() {
        return logRoot;
    }
}
