package android.rezkyaulia.com.hellokotlin.data.database.manage

import android.rezkyaulia.com.hellokotlin.data.database.MyDatabaseOpenHelper
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.data.model.Event
import com.google.gson.Gson
import io.reactivex.Observable
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.replace
import org.jetbrains.anko.db.select
import org.jetbrains.anko.error
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */

@Singleton
class ManageFavoriteEvent @Inject constructor(val db: MyDatabaseOpenHelper) : AnkoLogger{

    fun loadAll(): Observable<List<FavoriteEvent>> {
        var events = ArrayList<FavoriteEvent>()
        db.use {
            val result = select(FavoriteEvent.TABLE_FAVORITE).parseList(object : MapRowParser<List<FavoriteEvent>> {

                override fun parseRow(columns : Map<String, Any?>) : ArrayList<FavoriteEvent> {

                    val id = columns.getValue(FavoriteEvent.ID)
                    val eventId = columns.getValue(FavoriteEvent.EVENT_ID)
                    val homeTeam = columns.getValue(FavoriteEvent.EVENT_HOME_TEAM)
                    val awayTeam = columns.getValue(FavoriteEvent.EVENT_AWAY_TEAM)
                    val homeScore =columns.getValue(FavoriteEvent.EVENT_HOME_SCORE)
                    val awayScore =columns.getValue(FavoriteEvent.EVENT_AWAY_SCORE)
                    val date = columns.getValue(FavoriteEvent.EVENT_DATE)

                    val favEvent = FavoriteEvent(id as Long?, eventId as String?, date as String?, homeTeam as String?, awayTeam as String?, homeScore as String?, awayScore as String?)
                    events.add(favEvent)

                    return events
                }
            })

        }
        return Observable.just(events)
    }


    fun loadByEventId(id : String): Observable<List<FavoriteEvent>> {
        val events = ArrayList<FavoriteEvent>()
        db.use {
            select(FavoriteEvent.TABLE_FAVORITE)
                    .whereArgs("(${FavoriteEvent.EVENT_ID} = {id})",
                            "id" to id).parseList(object : MapRowParser<List<FavoriteEvent>> {

                        override fun parseRow(columns : Map<String, Any?>) : ArrayList<FavoriteEvent> {

                            val idLong = columns.getValue(FavoriteEvent.ID)
                            val eventId = columns.getValue(FavoriteEvent.EVENT_ID)
                            val homeTeam = columns.getValue(FavoriteEvent.EVENT_HOME_TEAM)
                            val awayTeam = columns.getValue(FavoriteEvent.EVENT_AWAY_TEAM)
                            val homeScore =columns.getValue(FavoriteEvent.EVENT_HOME_SCORE)
                            val awayScore =columns.getValue(FavoriteEvent.EVENT_AWAY_SCORE)
                            val date = columns.getValue(FavoriteEvent.EVENT_DATE)

                            val favEvent = FavoriteEvent(idLong as Long?, eventId as String?, date as String?, homeTeam as String?, awayTeam as String?, homeScore as String?, awayScore as String?)
                            events.add(favEvent)

                            return events
                        }
                    })

        }
        return Observable.fromCallable { events }
    }



    fun insert(event: Event) : Boolean = db.use {
        try {
            beginTransaction()
            val insertedId =  replace(FavoriteEvent.TABLE_FAVORITE,
                    FavoriteEvent.EVENT_ID to event.idEvent,
                    FavoriteEvent.EVENT_DATE to event.dateEvent,
                    FavoriteEvent.EVENT_HOME_TEAM to event.strHomeTeam,
                    FavoriteEvent.EVENT_AWAY_TEAM to event.strAwayTeam,
                    FavoriteEvent.EVENT_HOME_SCORE to event.intHomeScore,
                    FavoriteEvent.EVENT_AWAY_SCORE to event.intAwayScore )


            if (insertedId != -1L) {
                setTransactionSuccessful()
                true
            } else {
                throw RuntimeException("Fail to insert")
            }

        } finally {
            endTransaction()
        }
    }


    fun delete(id : String): Observable<Boolean> {
        var isDeleted = false
        db.use {
            try {
                beginTransaction()
                val result = delete(FavoriteEvent.TABLE_FAVORITE, "(${FavoriteEvent.EVENT_ID} = {id})",
                        "id" to id) > 0
                isDeleted = if (result) {
                    setTransactionSuccessful()
                    true
                } else {
                    false
                }

            } catch (e : Throwable) {
                isDeleted = false
                error{"cannot delete : ${Gson().toJson(e)}"}
            } finally {
                endTransaction()
            }
        }
        return Observable.fromCallable { isDeleted }
    }


}