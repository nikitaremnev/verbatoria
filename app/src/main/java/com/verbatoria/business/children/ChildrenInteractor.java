package com.verbatoria.business.children;

import com.verbatoria.data.repositories.children.IChildrenRepository;

/**
 * @author nikitaremnev
 */

public class ChildrenInteractor implements IChildrenInteractor {

    private static final String TAG = ChildrenInteractor.class.getSimpleName();

    private IChildrenRepository mChildrenRepository;

    public ChildrenInteractor(IChildrenRepository childrenRepository) {
        mChildrenRepository = childrenRepository;
    }

}
