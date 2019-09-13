package com.verbatoria.infrastructure.retrofit.endpoints.submit.model.params

import com.google.gson.annotations.SerializedName

/**
 * @author n.remnev
 */

data class BCIDataFileParamsDto(
    @SerializedName("file")
    var data: List<BCIDataItemParamsDto>
)