package com.example.app2.api.footballDataServiceInterfaces;

import com.example.app2.model.PartidoResultadoModel;

import java.util.List;

public interface ResultadosPasadosCallBack {
    void onSuccess(List<PartidoResultadoModel> partidoResultadoModelList);
    void onError(String error);
}
