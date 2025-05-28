package com.example.app2.api.footballDataServiceInterfaces;

import com.example.app2.model.ClasificacionModel;
import com.example.app2.model.LigaModel;

import java.util.ArrayList;

public interface ClasificacionCallBack
{
    void onSuccess(ArrayList<ClasificacionModel> clasificacionModelArrayList);
    void onError(String error);

}
