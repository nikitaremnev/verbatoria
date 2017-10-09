package com.verbatoria.data.repositories.children;

import com.verbatoria.data.network.request.ChildRequestModel;
import com.verbatoria.data.network.response.ChildResponseModel;
import com.verbatoria.data.network.response.ChildrenResponseModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IChildrenRepository {

    Observable<ChildResponseModel> addChild(String clientId, String accessToken, ChildRequestModel childRequestModel);

    Observable<ResponseBody> editChild(String clientId, String childId, String accessToken, ChildRequestModel childRequestModel);

    Observable<ChildrenResponseModel> searchClients(String query, String accessToken);

}
