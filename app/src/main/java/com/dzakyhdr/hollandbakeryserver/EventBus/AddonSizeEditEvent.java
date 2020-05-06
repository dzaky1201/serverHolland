package com.dzakyhdr.hollandbakeryserver.EventBus;

public class AddonSizeEditEvent {
    private boolean addonn;
    private int pos;

    public AddonSizeEditEvent(boolean addonn, int pos) {
        this.addonn = addonn;
        this.pos = pos;
    }

    public boolean isAddonn() {
        return addonn;
    }

    public void setAddonn(boolean addonn) {
        this.addonn = addonn;
    }

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }
}
