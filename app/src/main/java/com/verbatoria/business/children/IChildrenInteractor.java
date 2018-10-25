package com.verbatoria.business.children;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.data.network.common.ClientModel;

import java.util.List;

import okhttp3.ResponseBody;
import rx.Completable;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public interface IChildrenInteractor {

    Observable<ChildModel> addChild(ChildModel child);

    Completable editChild(ChildModel child);

    Observable<List<ChildModel>> searchChildren(String query);

    Observable<List<ChildModel>> getChild(ClientModel clientModel);

}
