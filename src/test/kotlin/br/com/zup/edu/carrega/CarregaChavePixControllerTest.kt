package br.com.zup.edu.carrega

import br.com.zup.edu.*
import br.com.zup.edu.compartilhado.KeyManagerGrpcFactory
import com.google.protobuf.Timestamp
import io.micronaut.context.annotation.Factory
import io.micronaut.context.annotation.Replaces
import io.micronaut.http.HttpRequest
import io.micronaut.http.HttpStatus
import io.micronaut.http.client.HttpClient
import io.micronaut.http.client.annotation.Client
import io.micronaut.test.extensions.junit5.annotation.MicronautTest
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.mockito.BDDMockito.given
import org.mockito.Mockito
import java.time.LocalDateTime
import java.time.LocalDateTime.*
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class CarregaChavePixControllerTest {

    @field:Inject
    lateinit var carregaChaveStub: CarregaChavePixServiceGrpc.CarregaChavePixServiceBlockingStub

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class CarregaStubFactory {
        @Singleton
        fun stubDetalhesMock() =
            Mockito.mock(CarregaChavePixServiceGrpc.CarregaChavePixServiceBlockingStub::class.java)
    }

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    private fun carregaChavePixResponse(clienteId: String, pixId: String): CarregaChavePixResponse {
        return CarregaChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .setPixId(pixId)
            .setChavePix(
                ChavePix.newBuilder()
                    .setChave("chave@email.com")
                    .setContaInfo(
                        ContaInfo.newBuilder()
                            .setTipoConta(TipoConta.CONTA_CORRENTE)
                            .setInstitucao("ITAU UNIBANCO")
                            .setNomeTitular("JacksonAlves")
                            .setCpfTitular("52624486070")
                            .setAgencia("0001")
                            .setNumeroConta("202020")
                            .build()
                    )
                    .setCriadaEm(now().let {
                        val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                        Timestamp.newBuilder()
                            .setSeconds(createdAt.epochSecond)
                            .setNanos(createdAt.nano)
                            .build()
                    })
            ).build()
    }


    @Test
    fun `deve carregar uma chave pix existente`() {

        val clienteId = UUID.randomUUID().toString()
        val pixId = UUID.randomUUID().toString()

        given(carregaChaveStub.carrega(Mockito.any())).willReturn(carregaChavePixResponse(clienteId, pixId))


        val request = HttpRequest.GET<Any>("/api/clientes/$clienteId/pix/$pixId")
        val response = client.toBlocking().exchange(request, Any::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
    }
}