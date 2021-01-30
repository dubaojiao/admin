package com.assistant.pojo;

import com.assistant.util.CheckUtil;

import java.io.Serializable;

public class FileUploadData implements Serializable {
    private String fileName;
    private String base64;
    private String type;

    public boolean isNull(){
        if(this == null){
            return true;
        }
        if(CheckUtil.stringIsNull(this.base64)){
            return true;
        }
        if(CheckUtil.stringIsNull(this.type)){
            return true;
        }
        return false;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64() {
        return base64;
    }

    public void setBase64(String base64) {
        this.base64 = base64;
    }
}
