package br.com.zup.edu.carrega

import br.com.zup.edu.CarregaChavePixResponse
import br.com.zup.edu.TipoConta
import io.micronaut.core.annotation.Introspected
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset

@Introspected
class DetalheChavePixResponse(chaveResponse: CarregaChavePixResponse) {
    val pixId = chaveResponse.pixId
    val tipoChave = chaveResponse.chavePix.tipoChave.name
    val chave = chaveResponse.chavePix.chave
    val criadaEm = chaveResponse.chavePix.criadaEm.let {
        LocalDateTime.ofInstant(Instant.ofEpochSecond(it.seconds, it.nanos.toLong()), ZoneOffset.UTC)
    }
    val conta = DetalhesContaResponse.toDetalhesConta(chaveResponse)
}

data class DetalhesContaResponse(
    var tipoConta: String,
    var instituicao: String,
    var nomeTitular: String,
    var cpfTitular: String,
    var agencia: String,
    var numeroConta: String
) {
    companion object {
        fun toDetalhesConta(chaveResponse: CarregaChavePixResponse): DetalhesContaResponse {
            return DetalhesContaResponse(
                tipoConta = chaveResponse.chavePix.contaInfo.tipoConta.name,
                instituicao = chaveResponse.chavePix.contaInfo.institucao,
                nomeTitular = chaveResponse.chavePix.contaInfo.nomeTitular,
                cpfTitular = chaveResponse.chavePix.contaInfo.cpfTitular,
                agencia = chaveResponse.chavePix.contaInfo.agencia,
                numeroConta = chaveResponse.chavePix.contaInfo.numeroConta
            )
        }
    }
}
