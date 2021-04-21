package br.com.zup.edu.lista

import br.com.zup.edu.*
import br.com.zup.edu.compartilhado.KeyManagerGrpcFactory
import br.com.zup.edu.registra.TipoContaRequest
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
import java.time.ZoneId
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@MicronautTest
internal class ListaChavePixControllerTest {

    @field:Inject
    lateinit var listaChaveStub: ListaChavePixServiceGrpc.ListaChavePixServiceBlockingStub

    @Factory
    @Replaces(factory = KeyManagerGrpcFactory::class)
    internal class ListaStubFactory {
        @Singleton
        fun stubDetalhesMock() =
            Mockito.mock(ListaChavePixServiceGrpc.ListaChavePixServiceBlockingStub::class.java)
    }

    @field:Inject
    @field:Client("/")
    lateinit var client: HttpClient

    private fun listaChavePixResponse(clienteId: String): ListaChavePixResponse {
        val chaveEmail = ListaChavePixResponse.ChavePixLista.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipoChave(TipoChave.EMAIL)
            .setChave("chave@email.com")
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .setCriadaEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()

        val chaveCelular = ListaChavePixResponse.ChavePixLista.newBuilder()
            .setPixId(UUID.randomUUID().toString())
            .setTipoChave(TipoChave.CELULAR)
            .setChave("+5534998989898")
            .setTipoConta(TipoConta.CONTA_CORRENTE)
            .setCriadaEm(LocalDateTime.now().let {
                val createdAt = it.atZone(ZoneId.of("UTC")).toInstant()
                Timestamp.newBuilder()
                    .setSeconds(createdAt.epochSecond)
                    .setNanos(createdAt.nano)
                    .build()
            })
            .build()


        return ListaChavePixResponse.newBuilder()
            .setClienteId(clienteId)
            .addAllChaves(listOf(chaveEmail, chaveCelular))
            .build()

    }


    @Test
    fun `deve listar todas as chaves pix existente`() {

        val clienteId = UUID.randomUUID().toString()

        val respostaGrpc = listaChavePixResponse(clienteId)

        given(listaChaveStub.lista(Mockito.any())).willReturn(respostaGrpc)


        val request = HttpRequest.GET<Any>("/api/clientes/$clienteId/pix/")
        val response = client.toBlocking().exchange(request, List::class.java)

        assertEquals(HttpStatus.OK, response.status)
        assertNotNull(response.body())
        assertEquals(response.body()!!.size, 2)
    }
}