package com.lopez.julz.inspectionv2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface ZonesDao {
    @Query("SELECT * FROM Zones ORDER BY Zone")
    List<Zones> getAll();

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Zones... zones);

    @Update
    void updateAll(Zones... zones);

    @Query("SELECT * FROM Zones WHERE id=:id")
    Zones getOne(String id);
}
