package com.lopez.julz.inspectionv2.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Zones {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "Zone")
    private String Zone;

    @ColumnInfo(name = "Notes")
    private String Notes;

    public Zones() {
    }

    public Zones(@NonNull String id, String zone, String notes) {
        this.id = id;
        Zone = zone;
        Notes = notes;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getZone() {
        return Zone;
    }

    public void setZone(String zone) {
        Zone = zone;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
