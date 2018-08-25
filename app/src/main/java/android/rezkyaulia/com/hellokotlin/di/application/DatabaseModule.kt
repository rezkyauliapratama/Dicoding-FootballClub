package android.rezkyaulia.com.hellokotlin.di.application

import android.content.Context
import android.rezkyaulia.com.hellokotlin.data.database.MyDatabaseOpenHelper
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by Rezky Aulia Pratama on 25/8/18.
 */
@Module
class DatabaseModule{


    @Provides
    @DatabaseInfo
    fun provideDatabaseName():String{
        return "football-db.db"
    }

    @Provides
    @DatabaseInfo
    fun provideDatabaseVersion():Int{
        return 2
    }

    @Singleton
    @Provides
    internal fun provideDatabaseHelper(@ApplicationContext context: Context, @DatabaseInfo name:String, @DatabaseInfo version: Int): MyDatabaseOpenHelper {
        return MyDatabaseOpenHelper(context,name,version)
    }
}