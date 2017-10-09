package com.verbatoria.business.children;

import com.verbatoria.business.dashboard.models.ChildModel;
import com.verbatoria.business.dashboard.processor.ModelsConverter;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.request.ChildRequestModel;
import com.verbatoria.data.repositories.children.IChildrenRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.RxSchedulers;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import rx.Observable;

/**
 * @author nikitaremnev
 */

public class ChildrenInteractor implements IChildrenInteractor {

    private static final String TAG = ChildrenInteractor.class.getSimpleName();

    private IChildrenRepository mChildrenRepository;
    private ITokenRepository mTokenRepository;

    public ChildrenInteractor(IChildrenRepository childrenRepository, ITokenRepository tokenRepository) {
        mChildrenRepository = childrenRepository;
        mTokenRepository = tokenRepository;
    }

    @Override
    public Observable<ChildModel> addChild(ChildModel child) {
        return mChildrenRepository.addChild(child.getClientId(), getAccessToken(), getChildRequestModel(child))
                .map(ModelsConverter::convertChildResponseModelToChildModel)
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<ResponseBody> editChild(ChildModel child) {
        return mChildrenRepository.editChild(child.getClientId(), child.getId(), getAccessToken(), getChildRequestModel(child))
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    @Override
    public Observable<List<ChildModel>> searchChildren(String query) {
        return mChildrenRepository.searchClients(query, getAccessToken())
                .map(childsResponseModel -> {
                    List<com.verbatoria.data.network.common.ChildModel> childModels = childsResponseModel.getChilds();
                    List<ChildModel> childModelResultList = new ArrayList<>();
                    for (int i = 0; i < childModels.size(); i ++) {
                        try {
                            childModelResultList.add(getChildModel(childModels.get(i)));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    return childModelResultList;
                })
                .subscribeOn(RxSchedulers.getNewThreadScheduler())
                .observeOn(RxSchedulers.getMainThreadScheduler());
    }

    private ChildRequestModel getChildRequestModel(ChildModel childModel) {
        return new ChildRequestModel()
                .setChild(getChildModel(childModel));
    }

    private com.verbatoria.data.network.common.ChildModel getChildModel(ChildModel childModel) {
        return new com.verbatoria.data.network.common.ChildModel()
                .setName(childModel.getName())
                .setId(childModel.getId())
                .setClientId(childModel.getClientId())
                .setBirthday(DateUtils.toString(childModel.getBirthday()));
    }

    private ChildModel getChildModel(com.verbatoria.data.network.common.ChildModel childModel) throws ParseException {
        return new ChildModel()
                .setName(childModel.getName())
                .setId(childModel.getId())
                .setClientId(childModel.getClientId())
                .setBirthday(DateUtils.parseDate(childModel.getBirthday()));
    }


    private String getAccessToken() {
        TokenModel tokenModel = mTokenRepository.getToken();
        return tokenModel.getAccessToken();
    }
}
