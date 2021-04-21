package br.com.zup.edu.lista

import br.com.zup.edu.ListaChavePixRequest
import br.com.zup.edu.ListaChavePixServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/clientes")
class ListaChavePixController(private val listaChavePixClient: ListaChavePixServiceGrpc.ListaChavePixServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/{clienteId}/pix/")
    fun lista(clienteId: UUID): HttpResponse<Any> {

        LOGGER.info("[$clienteId] listando chaves pix")
        val pix = listaChavePixClient.lista(
            ListaChavePixRequest.newBuilder()
                .setClienteId(clienteId.toString())
                .build()
        )

        val chaves = pix.chavesList.map { ChavePixResponse(it) }

        return HttpResponse.ok(chaves)
    }
}