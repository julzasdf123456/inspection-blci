package com.lopez.julz.inspectionv2.database;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Blocks {
    @PrimaryKey
    @NonNull
    private String id;

    @ColumnInfo(name = "Block")
    private String Block;

    @ColumnInfo(name = "Notes")
    private String Notes;

    public Blocks() {
    }

    public Blocks(@NonNull String id, String block, String notes) {
        this.id = id;
        Block = block;
        Notes = notes;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getBlock() {
        return Block;
    }

    public void setBlock(String block) {
        Block = block;
    }

    public String getNotes() {
        return Notes;
    }

    public void setNotes(String notes) {
        Notes = notes;
    }
}
