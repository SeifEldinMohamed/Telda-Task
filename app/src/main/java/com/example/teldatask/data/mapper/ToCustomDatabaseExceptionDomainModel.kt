package com.example.teldatask.data.mapper

import android.database.sqlite.SQLiteAccessPermException
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabaseCorruptException
import android.database.sqlite.SQLiteDatatypeMismatchException
import android.database.sqlite.SQLiteDiskIOException
import android.database.sqlite.SQLiteFullException
import android.database.sqlite.SQLiteMisuseException
import android.database.sqlite.SQLiteReadOnlyDatabaseException
import com.example.teldatask.domain.model.CustomDatabaseExceptionDomainModel

fun Throwable.toCustomDatabaseExceptionDomainModel(): CustomDatabaseExceptionDomainModel {
    return when (this) {
        is SQLiteConstraintException -> CustomDatabaseExceptionDomainModel.DatabaseConstraintExceptionDomainModel
        is SQLiteDatabaseCorruptException -> CustomDatabaseExceptionDomainModel.DatabaseCorruptExceptionDomainModel
        is SQLiteDiskIOException -> CustomDatabaseExceptionDomainModel.DatabaseDiskIOExceptionDomainModel
        is SQLiteFullException -> CustomDatabaseExceptionDomainModel.DatabaseFullExceptionDomainModel
        is SQLiteAccessPermException -> CustomDatabaseExceptionDomainModel.DatabaseAccessPermExceptionDomainModel
        is SQLiteReadOnlyDatabaseException -> CustomDatabaseExceptionDomainModel.DatabaseReadOnlyExceptionDomainModel
        is SQLiteDatatypeMismatchException -> CustomDatabaseExceptionDomainModel.DatabaseDatatypeMismatchExceptionDomainModel
        is SQLiteMisuseException -> CustomDatabaseExceptionDomainModel.DatabaseMisuseExceptionDomainModel

        else -> CustomDatabaseExceptionDomainModel.UnknownExceptionDomainModel
    }
}