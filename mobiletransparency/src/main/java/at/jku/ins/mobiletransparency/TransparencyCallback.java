package at.jku.ins.mobiletransparency;

import java.util.List;

import at.jku.ins.mobiletransparency.models.Tree;
import at.jku.ins.mobiletransparency.models.TreeInformation;

public interface TransparencyCallback {
    void onSuccess(Tree treeInformation);
    void onFailure();
}
