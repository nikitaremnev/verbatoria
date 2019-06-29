package com.verbatoria.business.children;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.data.network.common.ClientModel;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */

public interface IChildrenInteractor {

    Observable<ChildModel> addChild(ChildModel child);

    Completable editChild(ChildModel child);

    Observable<List<ChildModel>> searchChildren(String query);

    Observable<ArrayList<ChildModel>> getChild(ClientModel clientModel);

}
