package com.dzakyhdr.hollandbakeryserver.callback;

import com.dzakyhdr.hollandbakeryserver.model.CategoryModel;

import java.util.List;

public interface ICategoryCallbackListener {
    void onCategoryLoadSuccess(List<CategoryModel> categoryModelList);
    void onCategoryLoadFailed(String message);
}
