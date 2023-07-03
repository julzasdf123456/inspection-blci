package com.lopez.julz.inspectionv2.database;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OfflineUsersDao {
    @Query("SELECT * FROM OfflineUsers")
    List<OfflineUsers> getAll();

    @Insert
    void insertAll(OfflineUsers... users);

    @Update
    void updateAll(OfflineUsers... users);

    @Query("SELECT * FROM OfflineUsers WHERE UserId = :id")
    OfflineUsers getOne(String id);

    @Query("SELECT * FROM OfflineUsers ORDER BY id DESC LIMIT 1")
    OfflineUsers getFirst();

    @Query("SELECT * FROM OfflineUsers WHERE Username = :username AND Password = :password")
    OfflineUsers getOne(String username, String password);
}
