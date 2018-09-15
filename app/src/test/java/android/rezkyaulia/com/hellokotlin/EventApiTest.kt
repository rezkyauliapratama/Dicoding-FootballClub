package android.rezkyaulia.com.hellokotlin

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.Observer
import android.rezkyaulia.com.hellokotlin.Util.AppSchedulerProvider
import android.rezkyaulia.com.hellokotlin.data.DataManager
import android.rezkyaulia.com.hellokotlin.data.model.Event
import android.rezkyaulia.com.hellokotlin.data.model.EventResponse
import android.rezkyaulia.com.hellokotlin.data.network.NetworkApi
import android.rezkyaulia.com.hellokotlin.ui.UiStatus
import android.rezkyaulia.com.hellokotlin.ui.main.nextevent.NextEventViewModel
import com.nhaarman.mockitokotlin2.argumentCaptor
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
import retrofit2.HttpException
import javax.annotation.Nullable
import io.reactivex.observers.TestObserver




class EventApiTest{

    val LEAGUE_ID = "LEAGUEID"
    val TAG = "TAG"
    val URL = "URL"

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


    @Rule @JvmField var testSchedulerRule = RxImmediateSchedulerRule()

    @Mock
    lateinit var dataManager: DataManager
    @Mock
    lateinit var appSchedulerProvider: AppSchedulerProvider
    @Mock
    lateinit var networkApi: NetworkApi

    @Mock
    lateinit var UistatusObserver: Observer<UiStatus>

    @Mock
    lateinit var responseObserver: Observer<EventResponse>

    @InjectMocks
    lateinit var SUT : NextEventViewModel

    @Before
    fun setUp() {

//        SUT = NextEventViewModel(networkApi)

        success()
    }

    @Test
    fun eventSpecific_success_PassedToEndPoint() {
        val ac : ArgumentCaptor<String> = ArgumentCaptor.forClass(String::class.java)
        SUT.retrieveData(LEAGUE_ID)
        ac.apply {
            verify(networkApi).getEventNextLeague(capture())

            MatcherAssert.assertThat(value, CoreMatchers.`is`(LEAGUE_ID))
        }
    }

    @Test
    fun eventSpecific_init_showLoaderEvent() {
        SUT.uiStatusLD.observeForever(UistatusObserver)
        SUT.retrieveData(LEAGUE_ID)

        verify(UistatusObserver).onChanged(UiStatus.SHOW_LOADER)

    }

    @Test
    fun eventSpecific_success_hideLoaderEvent() {
        SUT.uiStatusLD.observeForever(UistatusObserver)
        SUT.retrieveData(LEAGUE_ID)

        verify(UistatusObserver).onChanged(UiStatus.HIDE_LOADER)

    }

    @Test
    fun eventSpecific_success_responseEvent() {
        val ac : ArgumentCaptor<EventResponse> = ArgumentCaptor.forClass(EventResponse::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            MatcherAssert.assertThat(value,CoreMatchers.`is`(response))

        }

    }

    @Test
    fun eventSpecific_success_sizeListValue() {
        val ac : ArgumentCaptor<EventResponse> = ArgumentCaptor.forClass(EventResponse::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            val eventResponse : EventResponse = value
            MatcherAssert.assertThat(eventResponse.events.size,CoreMatchers.`is`(response.events.size))

        }

    }

    @Test
    fun eventSpecific_success_EmptyListValue() {
        successReturnEmptyResponse()

        val ac : ArgumentCaptor<EventResponse> = ArgumentCaptor.forClass(EventResponse::class.java)
        SUT.eventResponseLD.observeForever(responseObserver)
        SUT.retrieveData(LEAGUE_ID)

        ac.apply {
            verify(responseObserver).onChanged(capture())
            val eventResponse : EventResponse = value
            MatcherAssert.assertThat(eventResponse.events.size,CoreMatchers.`is`(0))

        }

    }

    @Test
    fun eventSpecific_success_noInteractionWithResponseEvent() {
        failure()


        SUT.retrieveData(LEAGUE_ID)


        val testObserver = networkApi.getEventNextLeague(LEAGUE_ID).test()
        testObserver
                .assertError(Exception())


    }




    private fun success() {
        `when`(
                networkApi.getEventNextLeague(any(String::class.java))


        ).thenReturn(
                Single.create<EventResponse> { emitter ->
                    try {

                        emitter.onSuccess(
                                response
                        )

                    }catch (e: HttpException){
                        emitter.onError(e)
                    }
                    catch (e: Exception) {
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

                    }catch (e: HttpException){
                        emitter.onError(e)
                    }
                    catch (e: Exception) {
                        emitter.onError(e)
                    }
                })

    }

    private fun failure() {
        doThrow(Exception()).`when`(
                networkApi).getEventNextLeague(any(String::class.java)
        )
    }

}