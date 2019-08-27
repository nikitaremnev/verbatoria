package com.verbatoria.business.event.models.item

import com.verbatoria.ui.event.EventDetailMode

/**
 * @author n.remnev
 */

class EventDetailIncludeAttentionMemoryItem(
    mode: EventDetailMode,
    var isLoading: Boolean = false,
    var isAttentionMemoryIncluded: Boolean = false
) : EventDetailItem(mode)