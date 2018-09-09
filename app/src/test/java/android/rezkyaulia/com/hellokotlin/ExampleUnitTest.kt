package android.rezkyaulia.com.hellokotlin

import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.network.ApiRepository
import android.rezkyaulia.com.hellokotlin.data.network.api.EventApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import com.google.gson.Gson
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.subscribers.TestSubscriber
import org.hamcrest.CoreMatchers.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.junit.Test

import org.junit.Assert.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule
import org.junit.Rule
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement
import org.mockito.ArgumentMatchers
import org.mockito.Mockito.*
import java.lang.Exception


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest  {

    @Rule @JvmField
    val mockitoRule = MockitoJUnit.rule()

    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    var networkClient: NetworkClient? = null


    @Mock
    lateinit var eventApi:EventApi

    val jsonArr : String =
            "{\n" +
            "    \"events\": [\n" +
            " {\n" +
                    "            \"idEvent\": \"576504\",\n" +
                    "            \"idSoccerXML\": \"389889\",\n" +
                    "            \"strEvent\": \"Cardiff vs Arsenal\",\n" +
                    "            \"strFilename\": \"English Premier League 2018-09-01 Cardiff vs Arsenal\",\n" +
                    "            \"strSport\": \"Soccer\",\n" +
                    "            \"idLeague\": \"4328\",\n" +
                    "            \"strLeague\": \"English Premier League\",\n" +
                    "            \"strSeason\": \"1819\",\n" +
                    "            \"strDescriptionEN\": null,\n" +
                    "            \"strHomeTeam\": \"Cardiff\",\n" +
                    "            \"strAwayTeam\": \"Arsenal\",\n" +
                    "            \"intHomeScore\": \"2\",\n" +
                    "            \"intRound\": \"4\",\n" +
                    "            \"intAwayScore\": \"3\",\n" +
                    "            \"intSpectators\": null,\n" +
                    "            \"strHomeGoalDetails\": null,\n" +
                    "            \"strHomeRedCards\": null,\n" +
                    "            \"strHomeYellowCards\": null,\n" +
                    "            \"strHomeLineupGoalkeeper\": null,\n" +
                    "            \"strHomeLineupDefense\": null,\n" +
                    "            \"strHomeLineupMidfield\": null,\n" +
                    "            \"strHomeLineupForward\": null,\n" +
                    "            \"strHomeLineupSubstitutes\": null,\n" +
                    "            \"strHomeFormation\": null,\n" +
                    "            \"strAwayRedCards\": null,\n" +
                    "            \"strAwayYellowCards\": null,\n" +
                    "            \"strAwayGoalDetails\": null,\n" +
                    "            \"strAwayLineupGoalkeeper\": null,\n" +
                    "            \"strAwayLineupDefense\": null,\n" +
                    "            \"strAwayLineupMidfield\": null,\n" +
                    "            \"strAwayLineupForward\": null,\n" +
                    "            \"strAwayLineupSubstitutes\": null,\n" +
                    "            \"strAwayFormation\": null,\n" +
                    "            \"intHomeShots\": null,\n" +
                    "            \"intAwayShots\": null,\n" +
                    "            \"dateEvent\": \"2018-09-02\",\n" +
                    "            \"strDate\": \"02/09/18\",\n" +
                    "            \"strTime\": \"12:30:00+00:00\",\n" +
                    "            \"strTVStation\": null,\n" +
                    "            \"idHomeTeam\": \"133637\",\n" +
                    "            \"idAwayTeam\": \"133604\",\n" +
                    "            \"strResult\": null,\n" +
                    "            \"strCircuit\": null,\n" +
                    "            \"strCountry\": null,\n" +
                    "            \"strCity\": null,\n" +
                    "            \"strPoster\": null,\n" +
                    "            \"strFanart\": null,\n" +
                    "            \"strThumb\": null,\n" +
                    "            \"strBanner\": null,\n" +
                    "            \"strMap\": null,\n" +
                    "            \"strLocked\": \"unlocked\"\n" +
                    "        }"+
            "    ]\n" +
            "}"


    @Test
    fun testDoSomething() {
//        val response = Gson().fromJson(jsonArr, EventResponse::class.java)

        val single:Single<EventResponse> = Single.create<EventResponse> { emitter ->
            try {
                EventResponse(emptyList()).let { emitter.onSuccess(it) }

            } catch (e: Exception) {
                emitter.onError(e)
            }
        }
        `when`(eventApi
                .eventPastByLeagueId("", "")).thenReturn(single)


        val testSingle: Single<EventResponse> ? = eventApi
                .eventPastByLeagueId("asdasdf","" )

        testSingle!!.observeOn(AndroidSchedulers.mainThread())
                ?.subscribe({ result ->

                    assertThat(result.events,`is`(notNullValue()))


                }, { throwable ->

                    verify(eventApi, atLeastOnce().description("error "+ throwable.localizedMessage))

                })
    }
}


class RxImmediateSchedulerRule : TestRule {

    override fun apply(base: Statement, d: Description): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setComputationSchedulerHandler { Schedulers.trampoline() }
                RxJavaPlugins.setNewThreadSchedulerHandler { Schedulers.trampoline() }
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }

                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}