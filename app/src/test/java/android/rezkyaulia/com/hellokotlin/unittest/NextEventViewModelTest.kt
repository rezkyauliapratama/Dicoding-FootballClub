package android.rezkyaulia.com.hellokotlin.unittest

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.rezkyaulia.com.hellokotlin.ui.main.event.nextevent.NextEventViewModel
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




class NextEventViewModelTest{

    val LEAGUE_ID = "LEAGUEID"

    val response =
            listOf(
                    Event("1"),
                    Event("2"),
                    Event("3")
            )


    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()


    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var networkApi: NetworkApi

    @Mock
    lateinit var UistatusObserver: Observer<UiStatus>

    @Mock
    lateinit var responseObserver: Observer<List<Event>>

    @InjectMocks
    lateinit var SUT : NextEventViewModel

    @Before
    fun setUp() {

        success()
    }

    @Test
    fun nextEvent_success_PassedToEndPoint() {
        val ac : ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
        SUT.retrieveData(LEAGUE_ID)
        ac.apply {
            verify(networkApi).getEventNextLeague(capture())

            MatcherAssert.assertThat(value, CoreMatchers.`is`(LEAGUE_ID))
        }
    }

    @Test
    fun nextEvent_init_showLoaderEvent() {
        SUT.uiStatusLD.observeForever(UistatusObserver)
        SUT.retrieveData(LEAGUE_ID)

        verify(UistatusObserver).onChanged(UiStatus.SHOW_LOADER)

    }

    @Test
    fun nextEvent_success_hideLoaderEvent() {
        SUT.uiStatusLD.observeForever(UistatusObserver)
        SUT.retrieveData(LEAGUE_ID)

        verify(UistatusObserver).onChanged(UiStatus.HIDE_LOADER)

    }

    @Test
    fun nextEvent_success_responseLiveData() {
        val ac : ArgumentCaptor<List<Event>> = ArgumentCaptor.forClass(mutableListOf<Event>()::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            MatcherAssert.assertThat(value,CoreMatchers.`is`(response))

        }

    }

    @Test
    fun nextEvent_success_sizeListValue() {
        val ac : ArgumentCaptor<List<Event>> = ArgumentCaptor.forClass(mutableListOf<Event>()::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            val events : List<Event> = value
            MatcherAssert.assertThat(events.size,CoreMatchers.`is`(response.size))

        }

    }

    @Test
    fun nextEvent_success_EmptyListValue() {
        successReturnEmptyResponse()

        val ac : ArgumentCaptor<List<Event>> = ArgumentCaptor.forClass(mutableListOf<Event>()::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            val events : List<Event> = value
            MatcherAssert.assertThat(events.size,CoreMatchers.`is`(0))

        }

    }

    @Test
    fun nextEvent_failure_noInteractionWithResponseLiveData() {
        failure()


        SUT.retrieveData(LEAGUE_ID)

        SUT.eventResponseLD.observeForever(responseObserver)
        verifyNoMoreInteractions(responseObserver)


    }




    private fun success() {
        `when`(
                networkApi.getEventNextLeague(any(String::class.java))


        ).thenReturn(
                Single.create<EventResponse> { emitter ->
                    try {

                        emitter.onSuccess(
                                EventResponse(response)
                        )

                    }catch (e: Exception) {
                        emitter.onError(e)
                    }
                })



    }

    private fun successReturnEmptyResponse() {
        `when`(
                networkApi.getEventNextLeague(any(String::class.java))


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
                networkApi.getEventNextLeague(any(String::class.java)))
                .thenReturn(
                    Single.error(Exception())
                )
    }

}