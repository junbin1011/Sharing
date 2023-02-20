package com.jkb.junbin.sharing.model;

import java.io.Serializable;

public class FileInfo implements Serializable {
    public String fileName;
    public long fileSize;

    public FileInfo(String fileName, long fileSize) {
        this.fileName = fileName;
        this.fileSize = fileSize;
    }

    public FileInfo(String fileName) {
        this.fileName = fileName;
    }

    public FileInfo() {
    }
}


