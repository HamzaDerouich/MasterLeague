package com.example.app2.api.footballDataServiceInterfaces;

import com.example.app2.model.LigaModel;

import java.util.ArrayList;

public interface LigasCallback {
    void onSuccess(ArrayList<LigaModel> ligas);
    void onError(String error);
}
