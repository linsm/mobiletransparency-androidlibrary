// Copyright (c) 2022 Mario Lins <mario.lins@ins.jku.at>
//
// Licensed under the EUPL, Version 1.2.
//
// You may obtain a copy of the Licence at:
// https://joinup.ec.europa.eu/collection/eupl/eupl-text-eupl-12
package at.jku.ins.mobiletransparency.models;

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
