package com.dzakyhdr.hollandbakeryserver.callback;

import android.app.AlertDialog;
import android.widget.Button;
import android.widget.RadioButton;

import com.dzakyhdr.hollandbakeryserver.model.OrderModel;
import com.dzakyhdr.hollandbakeryserver.model.ShipperModel;

import java.util.List;

public interface IShipperLoadCallbackListener {
    void onShipperLoadSuccess(List<ShipperModel>shipperModelsList);
    void onShipperLoadSuccess(int pos, OrderModel orderModel, List<ShipperModel>shipperModels,
                              AlertDialog dialog,
                              Button btn_ok, Button btn_cancel,
                              RadioButton rdi_shipping, RadioButton rdi_shipped, RadioButton rdi_cancelled,RadioButton rdi_delete,RadioButton rdi_restore_placed);
    void onShipperLoadFailed(String message);
}
