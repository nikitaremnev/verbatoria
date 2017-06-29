package com.verbatoria.business.token.processor;

import android.content.Context;
import android.text.TextUtils;

import com.remnev.verbatoriamini.R;
import com.verbatoria.VerbatoriaApplication;
import com.verbatoria.business.dashboard.DashboardInteractor;
import com.verbatoria.business.dashboard.DashboardInteractorException;
import com.verbatoria.business.token.interactor.TokenInteractorException;
import com.verbatoria.business.token.models.TokenModel;
import com.verbatoria.data.network.response.LoginResponseModel;
import com.verbatoria.utils.DateUtils;
import com.verbatoria.utils.Logger;

import java.text.DateFormat;
import java.text.ParseException;

import javax.inject.Inject;

/**
 * Класс для валидации токена
 *
 * @author nikitaremnev
 */
public class TokenProcessor {

    private static final String TAG = TokenProcessor.class.getSimpleName();

    @Inject
    public Context mContext;

    public TokenProcessor() {
        VerbatoriaApplication.getApplicationComponent().inject(this);
    }

    public TokenModel obtainToken(LoginResponseModel loginResponseModel) {
        validateToken(loginResponseModel);
        TokenModel tokenModel = new TokenModel();
        tokenModel.setAccessToken(loginResponseModel.getAccessToken());
        try {
            Logger.e(TAG, "expiresToken: " + loginResponseModel.getExpiresToken());
            tokenModel.setExpireDate(DateUtils.parseDate(loginResponseModel.getExpiresToken()));
        } catch (ParseException e) {
            throw new TokenInteractorException(e.getMessage());
        }
        return tokenModel;
    }

    public TokenModel obtainToken(String accessToken, String expiryDate) {
        validateToken(accessToken, expiryDate);
        TokenModel tokenModel = new TokenModel();
        tokenModel.setAccessToken(accessToken);
        try {
            tokenModel.setExpireDate(DateUtils.parseDate(expiryDate));
        } catch (ParseException e) {
            throw new TokenInteractorException(e.getMessage());
        }
        return tokenModel;
    }

    private void validateToken(LoginResponseModel loginResponseModel) {
        if (loginResponseModel == null ||
                TextUtils.isEmpty(loginResponseModel.getAccessToken()) ||
                TextUtils.isEmpty(loginResponseModel.getExpiresToken())) {
            throw new TokenInteractorException(mContext.getString(R.string.token_empty_error));
        }
    }

    private void validateToken(String accessToken, String expiryDatel) {
        if (TextUtils.isEmpty(accessToken) ||
                TextUtils.isEmpty(expiryDatel)) {
            throw new TokenInteractorException(mContext.getString(R.string.token_empty_error));
        }
    }

}
