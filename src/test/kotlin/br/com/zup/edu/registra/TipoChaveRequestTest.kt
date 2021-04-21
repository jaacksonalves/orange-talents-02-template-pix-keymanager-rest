package br.com.zup.edu.registra

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

internal class TipoChaveRequestTest {

    @Nested
    inner class ChaveAleatoriaTest {

        @Test
        fun `DEVE ser valido quando chave ALEATORIA for nula ou vazia`() {
            val tipoChave = TipoChaveRequest.ALEATORIA

            assertTrue(tipoChave.valida(null))
            assertTrue(tipoChave.valida(""))
        }


        @Test
        fun `NAO deve ser valido quando chave ALEATORIA possuir valor`() {
            val tipoChave = TipoChaveRequest.ALEATORIA

            assertFalse(tipoChave.valida("chave"))
        }
    }


    @Nested
    inner class ChaveCpfTest{

        @Test
        fun `DEVE ser valido quando chave CPF for nula ou vazia`() {
            val tipoChave = TipoChaveRequest.CPF

            assertTrue(tipoChave.valida(null))
            assertTrue(tipoChave.valida(""))
        }


        @Test
        fun `NAO deve ser valido quando chave CPF possuir valor`() {
            val tipoChave = TipoChaveRequest.CPF

            assertFalse(tipoChave.valida("chave"))
        }

    }


    @Nested
    inner class ChaveCelularTest{

        @Test
        fun `DEVE ser valido quando tiver preenchimento E sem erros`(){
            val tipoChave = TipoChaveRequest.CELULAR

            assertTrue(tipoChave.valida("+5534998989898"))
            assertTrue(tipoChave.valida("+5511988558855"))
        }


        @Test
        fun `NAO deve ser valido quando nao for um numero de celular correto e completo`(){
            val tipoChave = TipoChaveRequest.CELULAR

            assertFalse(tipoChave.valida("teste"))
            assertFalse(tipoChave.valida(""))
            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida("5534998989898"))
            assertFalse(tipoChave.valida("+55349989a9898"))
        }
    }


    @Nested
    inner class ChaveEmailTest{

        @Test
        fun `DEVE ser valido quando tiver preenchimento E sem erros`(){
            val tipoChave = TipoChaveRequest.EMAIL

            assertTrue(tipoChave.valida("teste@email"))
            assertTrue(tipoChave.valida("Teste@Email.com"))
        }


        @Test
        fun `NAO deve ser valido quando nao for um email invalido ou incompleto ou vazio`(){
            val tipoChave = TipoChaveRequest.EMAIL

            assertFalse(tipoChave.valida("teste"))
            assertFalse(tipoChave.valida(""))
            assertFalse(tipoChave.valida(null))
            assertFalse(tipoChave.valida("testeemail.com"))
            assertFalse(tipoChave.valida("@email.com"))
        }
    }

}