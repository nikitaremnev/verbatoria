package com.verbatoria.business.token.processor;

import android.content.Context;
import android.text.TextUtils;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.token.interactor.TokenInteractorException;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.LoginResponseModel;

import javax.inject.Inject;

/**
 * Класс для валидации токена
 *
 * @author nikitaremnev
 */
public class TokenProcessor {

    @Inject
    public Context mContext;

    public TokenProcessor() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    public TokenModel obtainToken(LoginResponseModel loginResponseModel) {
        validateToken(loginResponseModel);
        TokenModel tokenModel = new TokenModel();
        tokenModel.setAccessToken(loginResponseModel.getAccessToken());
        tokenModel.setExpiresToken(loginResponseModel.getExpiresToken());
        return tokenModel;
    }

    private void validateToken(LoginResponseModel loginResponseModel) {
        if (loginResponseModel == null ||
                TextUtils.isEmpty(loginResponseModel.getAccessToken()) ||
                TextUtils.isEmpty(loginResponseModel.getExpiresToken())) {
            throw new TokenInteractorException(mContext.getString(R.string.token_empty_error));
        }
    }

}
