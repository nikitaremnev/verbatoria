package com.verbatoria.data.repositories.children;

import com.verbatoria.data.network.api.APIFactory;
import com.verbatoria.data.network.common.ChildModel;
import com.verbatoria.data.network.request.ChildRequestModel;
import com.verbatoria.data.network.response.ChildResponseModel;
import com.verbatoria.data.network.response.ChildrenResponseModel;

import io.reactivex.Completable;
import io.reactivex.Observable;


/**
 * @author nikitaremnev
 */
public class ChildrenRepository implements IChildrenRepository {

    public ChildrenRepository() {

    }

    @Override
    public Observable<ChildResponseModel> addChild(String clientId, String accessToken, ChildRequestModel childRequestModel) {
        return APIFactory.getAPIService().addChildRequest(clientId, accessToken, childRequestModel);
    }

    @Override
    public Completable editChild(String clientId, String childId, String accessToken, ChildRequestModel childRequestModel) {
        return Completable.fromObservable(APIFactory.getAPIService().editChildRequest(clientId, childId, accessToken, childRequestModel));
    }

    @Override
    public Observable<ChildModel> getChild(String clientId, String childId, String accessToken) {
        return APIFactory.getAPIService().getChildRequest(clientId, childId, accessToken);
    }

    @Override
    public Observable<ChildrenResponseModel> searchClients(String query, String accessToken) {
        return APIFactory.getAPIService().findChildRequest(query, accessToken);
    }

}
