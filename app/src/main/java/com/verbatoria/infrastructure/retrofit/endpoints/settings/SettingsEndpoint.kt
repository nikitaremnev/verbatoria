package com.verbatoria.infrastructure.retrofit.endpoints.settings

import com.verbatoria.infrastructure.retrofit.APIConstants
import com.verbatoria.infrastructure.retrofit.APIConstants.LOCATION_ID_PATH_KEY
import com.verbatoria.infrastructure.retrofit.endpoints.settings.model.params.SetLocationLanguageParamsDto
import okhttp3.ResponseBody
import retrofit2.http.*

/**
 * @author n.remnev
 */

interface SettingsEndpoint {

    @PUT(APIConstants.SET_LOCATION_LANGUAGE_URL)
    fun setLocationLanguage(
        @Path(value = LOCATION_ID_PATH_KEY) locationId: String,
        @Body setLocationLanguageParams: SetLocationLanguageParamsDto
    ): ResponseBody


}