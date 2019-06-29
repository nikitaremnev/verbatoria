package com.verbatoria.data.repositories.children;

import com.verbatoria.data.network.common.ChildModel;
import com.verbatoria.data.network.request.ChildRequestModel;
import com.verbatoria.data.network.response.ChildResponseModel;
import com.verbatoria.data.network.response.ChildrenResponseModel;

import io.reactivex.Completable;
import io.reactivex.Observable;

/**
 * @author nikitaremnev
 */
public interface IChildrenRepository {

    Observable<ChildResponseModel> addChild(String clientId, String accessToken, ChildRequestModel childRequestModel);

    Completable editChild(String clientId, String childId, String accessToken, ChildRequestModel childRequestModel);

    Observable<ChildModel> getChild(String clientId, String childId, String accessToken);

    Observable<ChildrenResponseModel> searchClients(String query, String accessToken);

}
