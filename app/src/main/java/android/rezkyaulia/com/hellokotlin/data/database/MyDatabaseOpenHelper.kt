package android.rezkyaulia.com.hellokotlin.data.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteTeam
import org.jetbrains.anko.db.*

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
class MyDatabaseOpenHelper (ctx: Context, name :String, version : Int) : ManagedSQLiteOpenHelper(ctx, name, null, version) {


    override fun onCreate(db: SQLiteDatabase) {
        // Here you create tables
        db.createTable(FavoriteTeam.TABLE_FAVORITE, true,
                FavoriteTeam.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteTeam.TEAM_ID to TEXT + UNIQUE,
                FavoriteTeam.TEAM_NAME to TEXT,
                FavoriteTeam.TEAM_BADGE to TEXT)

        db.createTable(FavoriteEvent.TABLE_FAVORITE, true,
                FavoriteEvent.ID to INTEGER + PRIMARY_KEY + AUTOINCREMENT,
                FavoriteEvent.EVENT_ID to TEXT + UNIQUE,
                FavoriteEvent.EVENT_HOME_TEAM to TEXT,
                FavoriteEvent.EVENT_AWAY_TEAM to TEXT,
                FavoriteEvent.EVENT_HOME_SCORE to TEXT,
                FavoriteEvent.EVENT_AWAY_SCORE to TEXT,
                FavoriteEvent.EVENT_DATE to TEXT)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Here you can upgrade tables, as usual
        db.dropTable(FavoriteTeam.TABLE_FAVORITE, true)
        db.dropTable(FavoriteEvent.TABLE_FAVORITE, true)
        db.createTable(FavoriteTeam.TABLE_FAVORITE, true)
        db.createTable(FavoriteEvent.TABLE_FAVORITE, true)
    }
}
