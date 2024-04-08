package com.lopez.julz.inspectionv2.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {LocalServiceConnections.class,
        LocalServiceConnectionInspections.class,
        OfflineUsers.class,
        Photos.class,
        Barangays.class,
        Towns.class,
        Users.class,
        Settings.class,
        MastPoles.class,
        PayTransactions.class,
        TotalPayments.class,
        Zones.class,
        Blocks.class,
        Materials.class
}, version = 28)
public abstract class AppDatabase extends RoomDatabase {
    public abstract ServiceConnectionsDao serviceConnectionsDao();

    public abstract ServiceConnectionInspectionsDao serviceConnectionInspectionsDao();

    public abstract OfflineUsersDao offlineUsersDao();

    public abstract PhotosDao photosDao();

    public abstract BarangaysDao barangaysDao();

    public abstract TownsDao townsDao();

    public abstract UsersDao usersDao();

    public abstract SettingsDao settingsDao();

    public abstract MastPolesDao mastPolesDao();

    public abstract PayTransactionsDao payTransactionsDao();

    public abstract TotalPaymentsDao totalPaymentsDao();

    public abstract BlocksDao blocksDao();

    public abstract ZonesDao zonesDao();

    public abstract MaterialsDao materialsDao();
}
