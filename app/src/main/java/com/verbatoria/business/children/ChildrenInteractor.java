package com.verbatoria.business.children;

import com.verbatoria.data.repositories.children.IChildrenRepository;
import com.verbatoria.data.repositories.token.ITokenRepository;

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

}
