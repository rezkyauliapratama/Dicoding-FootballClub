package android.rezkyaulia.com.hellokotlin.data.database.manage

import android.rezkyaulia.com.hellokotlin.data.database.MyDatabaseOpenHelper
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteTeam
import android.rezkyaulia.com.hellokotlin.data.model.Team
import io.reactivex.Observable
import org.jetbrains.anko.db.*
import java.util.concurrent.Callable
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 26/8/18.
 */
@Singleton
class ManageFavoriteTeam @Inject constructor(val db: MyDatabaseOpenHelper) {

    fun loadAll(): Observable<List<FavoriteTeam>> {
        var events = listOf<FavoriteTeam>()
        db.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            events = favorite
        }
        return Observable.fromCallable({ events })
    }

    fun loadById(id : String): Observable<List<FavoriteTeam>> {
        var events = listOf<FavoriteTeam>()
        db.use {
            val result = select(FavoriteTeam.TABLE_FAVORITE)
                    .whereArgs("(TEAM_ID = {id})",
                            "id" to id)
            val favorite = result.parseList(classParser<FavoriteTeam>())
            events = favorite
        }
        return Observable.fromCallable({ events })
    }


    fun insert(team: Team) : Boolean = db.use {
        try {
            beginTransaction()
            val insertedId =  replace(FavoriteTeam.TABLE_FAVORITE,
                    FavoriteTeam.TEAM_ID to team.teamId,
                    FavoriteTeam.TEAM_NAME to team.teamName,
                    FavoriteTeam.TEAM_BADGE to team.teamBadge)

            if (insertedId != -1L) {
                setTransactionSuccessful()
                true
            } else {
                false
                throw RuntimeException("Fail to insert")
            }

        } finally {
            endTransaction()
        }
    }


    fun delete(id : String): Observable<Boolean> {
        var isDeleted : Boolean? = null
        db.use {
            try {
                beginTransaction()
                val result = delete(FavoriteTeam.TABLE_FAVORITE, "(TEAM_ID = {id})",
                        "id" to id) > 0
                if (result) {
                    setTransactionSuccessful()
                    isDeleted = true
                } else {
                    isDeleted = false
                }

            } catch (e : Throwable) {
                isDeleted = false

            } finally {
                endTransaction()
            }
        }
        return Observable.fromCallable({ isDeleted })
    }
}