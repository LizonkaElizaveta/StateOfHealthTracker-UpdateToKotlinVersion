package stanevich.elizaveta.stateofhealthtracker.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.*
import stanevich.elizaveta.stateofhealthtracker.database.DbSchema.*


class MyDatabaseOpenHelper private constructor(ctx: Context) : ManagedSQLiteOpenHelper(ctx, DB_NAME, null, DB_VERSION) {

    init {
        instance = this
    }

    companion object {
        const val DB_NAME = "stateOfHealthTracker.db"
        const val DB_VERSION = 1

        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context) = instance ?: MyDatabaseOpenHelper(ctx.applicationContext)
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.createTable(
            MainActivityTable.TABLE_NAME, true,
            MainActivityTable.ID to INTEGER + PRIMARY_KEY,
            MainActivityTable.DATE to TEXT,
            MainActivityTable.TIME to TEXT,
            MainActivityTable.MOOD to TEXT,
            MainActivityTable.PILL to TEXT,
            MainActivityTable.DISKINEZIA to TEXT
        )

        db?.createTable(
            NotificationActivityTable.TABLE_NAME, true,
            NotificationActivityTable.ID to INTEGER + PRIMARY_KEY,
            NotificationActivityTable.TIME to TEXT,
            NotificationActivityTable.NAME_MEDICATION to TEXT
        )

        db?.createTable(
            AuthActivityTable.TABLE_NAME, true,
            AuthActivityTable.ID to INTEGER + PRIMARY_KEY,
            AuthActivityTable.FIRST_NAME to TEXT,
            AuthActivityTable.LAST_NAME to TEXT,
            AuthActivityTable.PHONE to TEXT,
            AuthActivityTable.MAIL to TEXT
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.dropTable(MainActivityTable.TABLE_NAME, true)
        db?.dropTable(NotificationActivityTable.TABLE_NAME, true)
        db?.dropTable(AuthActivityTable.TABLE_NAME, true)
        onCreate(db)
    }

}

// Access property for Context
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(this)