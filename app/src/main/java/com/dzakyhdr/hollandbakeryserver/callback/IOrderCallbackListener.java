package com.dzakyhdr.hollandbakeryserver.callback;

import com.dzakyhdr.hollandbakeryserver.model.CategoryModel;
import com.dzakyhdr.hollandbakeryserver.model.OrderModel;

import java.util.List;

public interface IOrderCallbackListener {
    void onOrderLoadSuccess(List<OrderModel> orderModelList);
    void onOrderLoadFailed(String message);
}
