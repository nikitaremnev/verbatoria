package com.verbatoria.business.dashboard.processor;

import com.verbatoria.business.dashboard.models.VerbatologModel;
import com.verbatoria.data.network.response.VerbatologEventResponseModel;
import com.verbatoria.data.network.response.VerbatologInfoResponseModel;
import java.util.List;

/**
 * Класс для конвертации моделей Вербатолога
 *
 * @author nikitaremnev
 */
public class VerbatologProcessor {

    public static VerbatologModel convertInfoResponseToVerbatologModel(VerbatologModel verbatologModel,
                                                                 VerbatologInfoResponseModel verbatologInfoResponseModel) {
        verbatologModel.setFirstName(verbatologInfoResponseModel.getFirstName());
        verbatologModel.setLastName(verbatologInfoResponseModel.getLastName());
        verbatologModel.setMiddleName(verbatologInfoResponseModel.getMiddleName());
        verbatologModel.setEmail(verbatologInfoResponseModel.getEmail());
        verbatologModel.setPhone(verbatologInfoResponseModel.getPhone());
        return verbatologModel;
    }

    public static VerbatologModel convertEventsResponseToVerbatologModel(VerbatologModel verbatologModel,
                                                                   List<VerbatologEventResponseModel> verbatologEventResponseModelList) {

        return verbatologModel;
    }

}
