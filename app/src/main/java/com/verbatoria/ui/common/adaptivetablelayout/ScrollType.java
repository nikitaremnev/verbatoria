package com.verbatoria.ui.common.adaptivetablelayout;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@IntDef({
        ScrollType.SCROLL_HORIZONTAL,
        ScrollType.SCROLL_VERTICAL
})
/**
 * Scroll type for drag and drop mode.
 */
@Retention(RetentionPolicy.SOURCE)
@interface ScrollType {
    int SCROLL_HORIZONTAL = 0;
    int SCROLL_VERTICAL = 1;
}
