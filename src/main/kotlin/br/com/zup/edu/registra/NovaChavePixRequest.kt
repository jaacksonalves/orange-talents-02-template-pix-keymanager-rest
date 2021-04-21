package br.com.zup.edu.registra

import br.com.zup.edu.RegistraChavePixRequest
import br.com.zup.edu.TipoChave
import br.com.zup.edu.TipoConta
import br.com.zup.edu.compartilhado.validacao.ValidPixKey
import io.micronaut.core.annotation.Introspected
import io.micronaut.validation.validator.constraints.EmailValidator
import java.util.*
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

@ValidPixKey
@Introspected
data class NovaChavePixRequest(
    @field:NotNull val tipoConta: TipoContaRequest,
    @field:Size(max = 77) val chave: String?,
    @field:NotNull val tipoChave: TipoChaveRequest
) {

    fun toModelGrpc(clienteId: UUID): RegistraChavePixRequest? {
        return RegistraChavePixRequest.newBuilder()
            .setClienteId(clienteId.toString())
            .setTipoConta(tipoConta.atributoGrpc)
            .setTipoChave(tipoChave.atributoGrpc)
            .setChave(chave ?: "")
            .build()
    }

}

enum class TipoChaveRequest(val atributoGrpc: TipoChave) {

    CPF(TipoChave.CPF) {
        override fun valida(chave: String?): Boolean {
            return chave.isNullOrBlank() //CPF não deve ser preenchido pois é o próprio CPF cadastrado do cliente
        }
    },

    CELULAR(TipoChave.CELULAR) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return chave.matches("^\\+[1-9][0-9]\\d{1,14}\$".toRegex())
        }
    },

    EMAIL(TipoChave.EMAIL) {
        override fun valida(chave: String?): Boolean {
            if (chave.isNullOrBlank()) {
                return false
            }
            return EmailValidator().run {
                initialize(null)
                isValid(chave, null)
            }
        }
    },


    ALEATORIA(TipoChave.ALEATORIA) {
        override fun valida(chave: String?) =
            chave.isNullOrBlank() // chave aleatória não deve ser preenchida pois é criada automaticamente
    };

    abstract fun valida(chave: String?): Boolean

}

enum class TipoContaRequest(val atributoGrpc: TipoConta) {
    CONTA_CORRENTE(TipoConta.CONTA_CORRENTE),
    CONTA_POUPANCA(TipoConta.CONTA_POUPANCA)
}
