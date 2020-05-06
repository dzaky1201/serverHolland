package com.dzakyhdr.hollandbakeryserver.EventBus;

import com.dzakyhdr.hollandbakeryserver.model.AddonModel;

import java.util.List;

public class UpdateAddonModel {
    private List<AddonModel> addonModel;

    public UpdateAddonModel() {
    }

    public List<AddonModel> getAddonModel() {
        return addonModel;
    }

    public void setAddonModel(List<AddonModel> addonModel) {
        this.addonModel = addonModel;
    }
}
