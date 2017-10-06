package com.verbatoria.business.children;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.data.network.response.MessageResponseModel;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public interface IChildrenInteractor {

    Observable<MessageResponseModel> addChild(ChildModel child);

    Observable<ResponseBody> editChild(ChildModel child);

//    Observable<ChildModel> getChild(String childId);

}
