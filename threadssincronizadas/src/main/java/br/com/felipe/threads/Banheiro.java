package br.com.felipe.threads;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Banheiro {

    private boolean isSujo = true;

    private Lock lock = new ReentrantLock();

    public void fazNumero1WithSincronized() {

        String nome = Thread.currentThread().getName();

        /*
        * Tudo que está dentro do bloco syncronized só poderá ser executado através de um único thread.
        * O synchronized(this) significa: "convidado, tente pegar a chave do banheiro"! Sem a chave,
        * o thread fica bloqueado, esperando a liberação.
        *
        * Em outras palavras, os comandos dentro do bloco serão executados de maneira atômica.
        * Quando o João sai do banheiro, ou seja, sai do bloco synchronized, ele devolve a chave.
        * Nesse momento o Pedro, que ficou bloqueado, pode entrar no banheiro e fazer as coisas que ele precisa fazer.
        *
        * A chave que usamos para sincronizar o acesso (this) também é chamado de Mutex.
        * O Mutex é utilizado para executar algum bloco de código de maneira atômica.
        * */

        // this representa a instância da classe banheiro...

        System.out.println(nome + " batendo na porta");

        synchronized (this) {
            System.out.println(nome + " entrando no banheiro");

            while (this.isSujo) {
                esperaLaFora(nome);
            }

            System.out.println(nome + " fazendo coisa rapida");


            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.isSujo = true; // sujando o banheiro
            System.out.println(nome + " dando descarga");
            System.out.println(nome + " lavando a mao");
            System.out.println(nome + " saindo do banheiro");
        }
    }

    public void fazNumero2WithSincronized() {

        String nome = Thread.currentThread().getName();

        System.out.println(nome + " batendo na porta");

        synchronized (this) {
            System.out.println(nome + " entrando no banheiro");

            while (this.isSujo) {
                esperaLaFora(nome);
            }

            System.out.println(nome + " fazendo coisa demorada");


            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            this.isSujo = true; // sujando o banheiro
            System.out.println(nome + " dando descarga");
            System.out.println(nome + " lavando a mao");
            System.out.println(nome + " saindo do banheiro");
        }
    }

    public void fazNumero1WithLock() {

        String nome = Thread.currentThread().getName();

        System.out.println(nome + " batendo na porta");

        /*
         *  Uma das principais diferenças entre lock e syncronized é a possibilidade
         *  de se criar um lock com timeout usando uma sobrecarga do método tryLock.

         *  Por exemplo:
         *
         *  boolean locked = lock.tryLock(5, TimeUnit.SECONDS); //5s
         *
         *  Com esse método esperamos até cinco segundos e receberemos true caso o lock for obtido.
         *  Caso contrário, receberemos false. Como desvantagem, há o fato de o programador ter a
         *  responsabilidade de liberar o lock (unlock()).
         * */

        lock.lock();
            System.out.println(nome + " entrando no banheiro");
            System.out.println(nome + " fazendo coisa rapida");

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            System.out.println(nome + " dando descarga");
            System.out.println(nome + " lavando a mao");
            System.out.println(nome + " saindo do banheiro");
        lock.unlock();

    }

    public void fazNumero2WithLock() {

        String nome = Thread.currentThread().getName();

        System.out.println(nome + " batendo na porta");

        lock.lock();
            System.out.println(nome + " entrando no banheiro");
            System.out.println(nome + " fazendo coisa demorada");

            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }


            System.out.println(nome + " dando descarga");
            System.out.println(nome + " lavando a mao");
            System.out.println(nome + " saindo do banheiro");
        lock.unlock();
    }

    public synchronized void limpa(){
        String nome = Thread.currentThread().getName();

        System.out.println(nome + " batendo na porta");


            System.out.println(nome + " entrando no banheiro");

            if (!this.isSujo) {
                System.out.println(nome + ", não está sujo, vou sair");
                return;
            }

            System.out.println(nome + " limpando o banheiro");
            this.isSujo = false;

            try {
                Thread.sleep(13000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            /* Esse método notifica todas as threads que estão em modo de espera (wait)...
            *  Se usássemos o método notify, ele notificaria apenas a útilma thread que tentou acessar o banheiro
            *
            * */
            this.notifyAll();

            System.out.println(nome + " saindo do banheiro");
    }

    private void esperaLaFora(String nome) {

        System.out.println(nome + ", eca, banheiro está sujo");
        try {
            /*
            * Quando um thread executa o método wait(), ele sabe que tem que devolver a chave e esperar.
            * O thread fica no estado de espera. Pense que existe um banco na frente do banheiro, onde
            * os convidados ficam esperando até alguém limpar o banheiro!
            * */
            this.wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}