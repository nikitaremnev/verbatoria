package com.verbatoria.data.repositories.children;

import com.verbatoria.data.network.common.ChildModel;
import com.verbatoria.data.network.request.ChildRequestModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */
public interface IChildrenRepository {

    Observable<ResponseBody> addChild(String clientId, String accessToken, ChildRequestModel childRequestModel);

    Observable<ResponseBody> editChild(String clientId, String childId, String accessToken, ChildRequestModel childRequestModel);

    Observable<ChildModel> getChild(String clientId, String childId, String accessToken);

}
