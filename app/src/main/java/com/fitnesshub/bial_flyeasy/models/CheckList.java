package com.fitnesshub.bial_flyeasy.models;

public class CheckList {
    boolean checked;
    String item;

    public CheckList(boolean checked, String item) {
        this.checked = checked;
        this.item = item;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
