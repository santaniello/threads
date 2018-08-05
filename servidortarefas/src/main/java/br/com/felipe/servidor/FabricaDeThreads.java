package br.com.felipe.servidor;

import br.com.felipe.exceptions.TratadorDeExcecao;

import java.util.concurrent.ThreadFactory;
/*
* Classe responsável por manipular a criação de threads dentro do pool.
*
* Aqui nós podemos colocar todas as configurações para a criação de uma nova thread dentro do pool.
* */
public class FabricaDeThreads implements ThreadFactory {

    private static int numero = 1;

    @Override
    public Thread newThread(Runnable r) {

        Thread thread = new Thread(r, "Thread Servidor Tarefas " + numero);

        numero++;

        thread.setUncaughtExceptionHandler(new TratadorDeExcecao());

        return thread;
    }
}