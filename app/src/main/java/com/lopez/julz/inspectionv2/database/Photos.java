package com.lopez.julz.inspectionv2.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.io.File;

@Entity
public class Photos {
    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @ColumnInfo(name = "Path")
    private String Path;

    @ColumnInfo(name = "ServiceConnectionId")
    private String ServiceConnectionId;

    @ColumnInfo(name = "UploadStatus")
    private String UploadStatus;

    public Photos() {
    }

    public Photos(String path, String serviceConnectionId, String uploadStatus) {
        Path = path;
        ServiceConnectionId = serviceConnectionId;
        UploadStatus = uploadStatus;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return Path;
    }

    public void setPath(String path) {
        Path = path;
    }

    public String getServiceConnectionId() {
        return ServiceConnectionId;
    }

    public void setServiceConnectionId(String serviceConnectionId) {
        ServiceConnectionId = serviceConnectionId;
    }

    public String getUploadStatus() {
        return UploadStatus;
    }

    public void setUploadStatus(String uploadStatus) {
        UploadStatus = uploadStatus;
    }
}
