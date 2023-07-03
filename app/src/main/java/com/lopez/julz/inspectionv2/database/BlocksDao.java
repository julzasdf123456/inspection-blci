package com.lopez.julz.inspectionv2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface BlocksDao {
    @Query("SELECT * FROM Blocks ORDER BY Block")
    List<Blocks> getAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Blocks... blocks);

    @Update
    void updateAll(Blocks... blocks);

    @Query("SELECT * FROM Blocks WHERE id=:id")
    Blocks getOne(String id);
}
