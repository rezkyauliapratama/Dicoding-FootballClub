package android.rezkyaulia.com.hellokotlin.unittest

import android.rezkyaulia.com.hellokotlin.ui.main.event.lastevent.LastEventViewModel

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import io.reactivex.Single
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import org.mockito.*
import org.mockito.Mockito.*
import org.mockito.junit.MockitoJUnit

/**
 * Created by Rezky Aulia Pratama on 16/9/18.
 */
class LastEventViewModelTest{

    val LEAGUE_ID = "LEAGUEID"

    val response = EventResponse(
            listOf(
                    Event("1"),
                    Event("2"),
                    Event("3")
            )
    )

    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Rule
    @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var networkApi: NetworkApi

    @Mock
    lateinit var UistatusObserver: Observer<UiStatus>

    @Mock
    lateinit var responseObserver: Observer<EventResponse>

    @InjectMocks
    lateinit var SUT : LastEventViewModel

    @Before
    fun setUp() {
        success()
    }

    @Test
    fun lastEvent_success_leagueIdPassedToEndPoint() {
        val ac : ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
        SUT.retrieveData(LEAGUE_ID)
        ac.apply {
            verify(networkApi).getEventPastLeague(capture())

            MatcherAssert.assertThat(value, CoreMatchers.`is`(LEAGUE_ID))
        }
    }

    @Test
    fun lastEvent_init_showLoaderEvent() {
        SUT.uiStatusLD.observeForever(UistatusObserver)
        SUT.retrieveData(LEAGUE_ID)

        verify(UistatusObserver).onChanged(UiStatus.SHOW_LOADER)

    }

    @Test
    fun lastEvent_success_hideLoaderEvent() {
        SUT.uiStatusLD.observeForever(UistatusObserver)
        SUT.retrieveData(LEAGUE_ID)

        verify(UistatusObserver).onChanged(UiStatus.HIDE_LOADER)

    }

    @Test
    fun lastEvent_success_responseLiveData() {
        val ac : ArgumentCaptor<EventResponse> = ArgumentCaptor.forClass(EventResponse::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            MatcherAssert.assertThat(value, CoreMatchers.`is`(response))

        }

    }

    @Test
    fun lastEvent_success_sizeListValue() {
        val ac : ArgumentCaptor<EventResponse> = ArgumentCaptor.forClass(EventResponse::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            val eventResponse : EventResponse = value
            MatcherAssert.assertThat(eventResponse.events.size, CoreMatchers.`is`(response.events.size))

        }

    }

    @Test
    fun lastEvent_success_EmptyListValue() {
        successReturnEmptyResponse()

        val ac : ArgumentCaptor<EventResponse> = ArgumentCaptor.forClass(EventResponse::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            val eventResponse : EventResponse = value
            MatcherAssert.assertThat(eventResponse.events.size, CoreMatchers.`is`(0))

        }

    }

    @Test
    fun lastEvent_failure_noInteractionWithResponseLiveData() {
        failure()


        SUT.retrieveData(LEAGUE_ID)

        SUT.eventResponseLD.observeForever(responseObserver)
        verifyNoMoreInteractions(responseObserver)


    }




    private fun success() {
        `when`(
                networkApi.getEventPastLeague(any(String::class.java))
        ).thenReturn(
                Single.create<EventResponse> { emitter ->
                    try {

                        emitter.onSuccess(
                                response
                        )

                    } catch (e: Exception) {
                        emitter.onError(e)
                    }
                })



    }

    private fun successReturnEmptyResponse() {
        `when`(
                networkApi.getEventPastLeague(any(String::class.java))


        ).thenReturn(
                Single.create<EventResponse> { emitter ->
                    try {

                        emitter.onSuccess(
                                EventResponse(emptyList())
                        )

                    }catch (e: Exception) {
                        emitter.onError(e)
                    }
                })

    }

    private fun failure() {
        `when`(
                networkApi.getEventPastLeague(any(String::class.java)))
                .thenReturn(
                        Single.error(Exception())
                )
    }

}