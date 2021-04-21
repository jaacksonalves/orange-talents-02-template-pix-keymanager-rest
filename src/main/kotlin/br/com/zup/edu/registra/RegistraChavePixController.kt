package br.com.zup.edu.registra

import br.com.zup.edu.KeyManagerGrpcServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Body
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.PathVariable
import io.micronaut.http.annotation.Post
import io.micronaut.validation.Validated
import org.slf4j.LoggerFactory
import java.net.URI
import java.util.*
import javax.inject.Inject
import javax.validation.Valid

@Validated
@Controller("api/pix/clientes")
class RegistraChavePixController(@Inject private val registraChavePixClient: KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Post("/{clienteId}/pix")
    fun registraChavePix(@PathVariable clienteId: UUID, @Valid @Body request: NovaChavePixRequest): HttpResponse<Any> {
        LOGGER.info("[$clienteId] criando uma nova chave pix com $request")

        val grpcResponse = registraChavePixClient.registra(request.toModelGrpc(clienteId))

        return HttpResponse.created(location(clienteId, grpcResponse.pixId))
    }
}


private fun location(clienteId: UUID, pixId: String): URI {
    return HttpResponse.uri("api/pix/clientes/$clienteId/pix/$pixId")
}