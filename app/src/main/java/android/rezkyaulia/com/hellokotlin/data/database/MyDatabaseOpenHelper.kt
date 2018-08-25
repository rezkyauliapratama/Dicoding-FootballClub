package android.rezkyaulia.com.hellokotlin.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.rezkyaulia.com.hellokotlin.data.model.Favorite
import org.jetbrains.anko.db.*

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
class MyDatabaseOpenHelper (ctx: Context, name :String, version : Int) : ManagedSQLiteOpenHelper(ctx, name, null, version) {
    /*companion object {
        private var instance: MyDatabaseOpenHelper? = null

        @Synchronized
        fun getInstance(ctx: Context): MyDatabaseOpenHelper {
            if (instance == null) {
                instance = MyDatabaseOpenHelper(ctx.applicationContext)
            }
            return instance as MyDatabaseOpenHelper
        }
    }*/

    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(Favorite.TABLE_FAVORITE, true,
                Favorite.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                Favorite.TEAM_ID to TEXT + UNIQUE,
                Favorite.TEAM_NAME to TEXT,
                Favorite.TEAM_BADGE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(Favorite.TABLE_FAVORITE, true)
    }
}

// Access property for Context
/*
val Context.database: MyDatabaseOpenHelper
    get() = MyDatabaseOpenHelper.getInstance(applicationContext)*/
