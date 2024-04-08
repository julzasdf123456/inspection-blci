package com.lopez.julz.inspectionv2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface MaterialsDao {
    @Query("SELECT * FROM Materials WHERE id=:id")
    Materials getOne(String id);

    @Query("SELECT * FROM Materials WHERE ServiceConnectionId=:scId")
    List<Materials> getByServiceConnectionId(String scId);

    @Query("SELECT * FROM Materials WHERE ServiceConnectionId=:scId")
    Materials getOneByServiceConnectionId(String scId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(Materials... material);

    @Update
    void updateAll(Materials... material);

    @Query("DELETE FROM Materials WHERE id=:id")
    void delete(String id);
}
