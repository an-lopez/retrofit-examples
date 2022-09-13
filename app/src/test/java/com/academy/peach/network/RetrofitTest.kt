package com.academy.peach.network

import com.academy.peach.network.model.network.response.Character
import com.academy.peach.network.model.network.response.ModelInfo
import com.academy.peach.network.model.network.response.ModelWrapper
import com.academy.peach.network.network.service.RickAndMortyClient
import com.academy.peach.network.network.service.RickAndMortyService
import com.academy.peach.network.util.ApiSuccess
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.nio.charset.StandardCharsets
import java.util.concurrent.TimeUnit


class RetrofitTest {

    private val mockWebServer = MockWebServer()

    private val client = OkHttpClient.Builder()
        .connectTimeout(1, TimeUnit.SECONDS)
        .readTimeout(1, TimeUnit.SECONDS)
        .writeTimeout(1, TimeUnit.SECONDS)
        .build()

    private val service = Retrofit.Builder()
        .baseUrl(mockWebServer.url("/"))
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create<RickAndMortyService>()

    private val networkDataSource = RickAndMortyClient(service)

    @Before
    fun init() {

    }

    @Test
    fun `test object is complete`() {
        mockWebServer.enqueueResponse(200, "get-characters-200.json")
        runBlocking {
            val actual = networkDataSource.getCharacters()
            val expected = ApiSuccess(
                ModelWrapper(
                    info = ModelInfo(
                        count = 826,
                        pages = 42,
                        next = "https://rickandmortyapi.com/api/character?page=2",
                        previous = null
                    ),
                    results = listOf(
                        Character(
                            name = "Rick Sanchez",
                            image = "https://rickandmortyapi.com/api/character/avatar/1.jpeg"
                        )
                    )
                )
            )
            assert(actual == expected)
        }
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }
}

fun MockWebServer.enqueueResponse(code: Int, file: String) {
    val inputStream = javaClass.classLoader?.getResourceAsStream("api-response/$file")
    val source = inputStream?.source()?.buffer()
    source?.let {
        enqueue(
            MockResponse().apply {
                setResponseCode(code)
                setBody(source.readString(StandardCharsets.UTF_8))
            }
        )
    }
}