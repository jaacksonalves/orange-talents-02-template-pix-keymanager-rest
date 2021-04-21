package br.com.zup.edu.carrega

import br.com.zup.edu.CarregaChavePixRequest
import br.com.zup.edu.CarregaChavePixServiceGrpc
import io.micronaut.http.HttpResponse
import io.micronaut.http.annotation.Controller
import io.micronaut.http.annotation.Get
import org.slf4j.LoggerFactory
import java.util.*

@Controller("/api/clientes")
class CarregaChavePixController(private val carregaChavePixClient: CarregaChavePixServiceGrpc.CarregaChavePixServiceBlockingStub) {

    private val LOGGER = LoggerFactory.getLogger(this::class.java)

    @Get("/{clienteId}/pix/{pixId}")
    fun carregaCavePix(clienteId: UUID, pixId: UUID): HttpResponse<Any> {
        LOGGER.info("[$clienteId] carregando chave pix de ID [$pixId]")

        val carregaChaveResponse = carregaChavePixClient.carrega(
            CarregaChavePixRequest.newBuilder()
                .setPixEClienteId(
                    CarregaChavePixRequest.FiltroPorPixEClienteId.newBuilder()
                        .setClienteId(clienteId.toString())
                        .setPixId(pixId.toString())
                        .build()
                )
                .build()
        )

        return HttpResponse.ok(DetalheChavePixResponse(carregaChaveResponse))
    }
}