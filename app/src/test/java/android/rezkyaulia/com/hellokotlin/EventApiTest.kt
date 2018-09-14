package android.rezkyaulia.com.hellokotlin

import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.network.api.EventApi
import android.rezkyaulia.com.hellokotlin.data.network.api.TheSportDBApi
import com.rezkyaulia.android.light_optimization_data.NetworkClient
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.*


class EventApiTest{

    val LEAGUE_ID = "LEAGUEID"
    val TAG = "TAG"
    val URL = "URL"



    lateinit var networkClientMock: NetworkClient

    lateinit var SUT : EventApi

    @Before
    fun setUp() {
        networkClientMock = Mockito.mock(NetworkClient::class.java)
        SUT = EventApi(networkClientMock)
        success()
    }

    @Test
    fun eventSpecific_success_PassedToEndPoint() {
        val ac : ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)

        SUT.eventSpecific(URL)


        ac.apply {
            verify(networkClientMock).withUrl(capture())
                    .initAs(EventResponse::class.java)
                    .setTag(capture())
                    .syncFuture

            val captures = allValues
            MatcherAssert.assertThat(captures[0], CoreMatchers.`is`(LEAGUE_ID))
            MatcherAssert.assertThat(captures[1], CoreMatchers.`is`(TAG))
        }





    }

    private fun success() {
        `when`(networkClientMock.withUrl(any(String::class.java))
                .initAs(Any::class.java)
                .setTag(any(String::class.java))
                .syncFuture).thenReturn(Any())
    }
}