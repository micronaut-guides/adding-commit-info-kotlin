package example.micronaut

import io.micronaut.context.ApplicationContext
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.runtime.server.EmbeddedServer
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.on
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class HomeControllerSpec: Spek({
    describe("info endpoint") {
        var embeddedServer : EmbeddedServer = ApplicationContext.run(EmbeddedServer::class.java) // <1>
        var client : HttpClient  = HttpClient.create(embeddedServer.url) // <2>
        on("test git commit info appears in JSON") {
            val request: HttpRequest<Any> = HttpRequest.GET("/info") // <3>
            var rsp = client.toBlocking().exchange(request, Map::class.java)

            assertEquals(rsp.status(), HttpStatus.OK)

            val json : Map<String, Any> = rsp.body() as Map<String, Any>// <4>

            assertNotNull(json["git"])

            val mapCommit = (json["git"] as Map<String, Any>)["commit"] as Map<String, Object>
            assertNotNull(mapCommit)
            assertNotNull(mapCommit["message"])
            assertNotNull(mapCommit["time"])
            assertNotNull(mapCommit["id"])
            assertNotNull(mapCommit["user"])
            assertNotNull((json["git"] as Map<String, Any>)["branch"])

        }
        afterGroup {
            client.close()
            embeddedServer.close()
        }

    }
})