package android.rezkyaulia.com.hellokotlin.data.database.manage

import android.content.Context
import android.rezkyaulia.com.hellokotlin.data.database.MyDatabaseOpenHelper
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteEvent
import android.rezkyaulia.com.hellokotlin.data.database.entity.FavoriteTeam
import io.reactivex.Flowable
import org.jetbrains.anko.AnkoComponent
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */

@Singleton
class ManageFavoriteEvent @Inject constructor(val db: MyDatabaseOpenHelper) {


}