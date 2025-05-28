package com.example.app2.api.footballDataServiceInterfaces;

import com.example.app2.model.GoleadorModel;
import com.example.app2.model.LigaModel;

import java.util.ArrayList;

public interface MaximosGoleadoresCallBack {
    void onSuccess(ArrayList<GoleadorModel> goleadorModelArrayList);
    void onError(String error);
}

