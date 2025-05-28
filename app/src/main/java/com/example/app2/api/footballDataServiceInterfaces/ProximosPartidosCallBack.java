package com.example.app2.api.footballDataServiceInterfaces;

import com.example.app2.model.LigaModel;
import com.example.app2.model.PartidoResultadoModel;

import java.util.ArrayList;

public interface ProximosPartidosCallBack {
    void onSuccess(ArrayList<PartidoResultadoModel> partidoResultadoModelArrayList);
    void onError(String error);
}

