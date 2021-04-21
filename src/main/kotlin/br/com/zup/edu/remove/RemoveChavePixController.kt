package br.com.zup.edu.remove

import br.com.zup.edu.RemoveChavePixRequest
import br.com.zup.edu.RemoveChavePixServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Delete
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/clientes")
class RemoveChavePixController(private val removeChavePixClient: RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Delete("/{clienteId}/pix/{pixId}")
    fun removeChavePix(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        LOGGER.info("[$clienteId] removendo chave pix de ID [$pixId]")

        removeChavePixClient.remove(
            RemoveChavePixRequest.newBuilder()
                .setPixId(pixId.toString())
                .setClienteId(clienteId.toString())
                .build()
        )

        return HttpResponse.ok()
    }
}