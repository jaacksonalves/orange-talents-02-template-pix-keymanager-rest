package br.com.zup.edu.compartilhado

import br.com.zup.edu.CarregaChavePixServiceGrpc
import br.com.zup.edu.KeyManagerGrpcServiceGrpc
import br.com.zup.edu.ListaChavePixServiceGrpc
import br.com.zup.edu.RemoveChavePixServiceGrpc
import io.grpc.ManagedChannel
import io.micronaut.context.annotation.Factory
import io.micronaut.grpc.annotation.GrpcChannel
import javax.inject.Singleton

@Factory
class KeyManagerGrpcFactory(@GrpcChannel("keyManager") val channel: ManagedChannel) {

    @Singleton
    fun registraChave(): KeyManagerGrpcServiceGrpc.KeyManagerGrpcServiceBlockingStub =
        KeyManagerGrpcServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun deletaChave(): RemoveChavePixServiceGrpc.RemoveChavePixServiceBlockingStub =
        RemoveChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun carregaChave(): CarregaChavePixServiceGrpc.CarregaChavePixServiceBlockingStub =
        CarregaChavePixServiceGrpc.newBlockingStub(channel)

    @Singleton
    fun listaChaves(): ListaChavePixServiceGrpc.ListaChavePixServiceBlockingStub =
        ListaChavePixServiceGrpc.newBlockingStub(channel)


}
