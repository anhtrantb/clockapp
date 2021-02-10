package com.example.clockapp;

public class ItemSelect {
    String content;
    String uri;
    boolean isChecked;

    public ItemSelect(String content){
        this.content = content;
    }

    public ItemSelect(String content, String uri) {
        this.content = content;
        this.uri = uri;
    }
    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
