package com.sample.simpsonviewer.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RelatedTopic {
    @SerializedName("Text")
    @Expose
    private String text;
    @SerializedName("Result")
    @Expose
    private String result;
    @SerializedName("FirstURL")
    @Expose
    private String firstURL;
    @SerializedName("Icon")
    @Expose
    private Icon icon;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFirstURL() {
        return firstURL;
    }

    public void setFirstURL(String firstURL) {
        this.firstURL = firstURL;
    }

    public Icon getIcon() {
        return icon;
    }

    public void setIcon(Icon icon) {
        this.icon = icon;
    }

}
