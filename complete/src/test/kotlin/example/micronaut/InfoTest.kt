package example.micronaut

import io.micronaut.http.HttpRequest
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.annotation.MicronautTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import javax.inject.Inject

@MicronautTest // <1>
class InfoTest {
    @Inject
    @field:Client("/")
    lateinit var client : HttpClient // <2>

    @Test
    fun testGitComitInfoAppearsInJson() {
        val request: HttpRequest<*> = HttpRequest.GET<Any>("/info") // <3>
        val rsp = client.toBlocking().exchange(request, Info::class.java)
        assertEquals(200, rsp.status().code)
        val info = rsp.body() // <4>
        assertNotNull(info)
        assertNotNull(info!!.git)
        assertNotNull(info.git!!.commit)
        assertNotNull(info.git!!.commit!!.id)
    }
}