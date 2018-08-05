package br.com.felipe.deadlock;

public class PrincipalBanco {

    public static void main(String[] args) {
        GerenciadorDeTransacao tx = new GerenciadorDeTransacao();
        PoolDeConexao pool = new PoolDeConexao();

        /*
        * o deadlock ocorre porque um thread impede a execução do outro pois cada um possui a chave de um objeto que
        * o outro precisa para continuar. Temos um impasse e esse impasse é chamado de deadlock.
        *
        * No exemplo abaixo, a classe TarefaAcessaBanco pega a chave (mutex) do pool primeiro e depois do tx
        * e na thread TarefaAcessaBancoProcedimento a ordem está invertida, primeiro é obtida a chave de tx e depois do pool
        * logo isso acaba gerando um conflito e a execução da aplicação (das threads na verdade) nuca é encerrada.
        *
        * Para resolver este problema basta apenas colocar o acesso aos objetos (pool e tx) na mesma ordem em ambas as threads.        *
        *
        * */
        new Thread(new TarefaAcessaBanco(pool, tx)).start();
        new Thread(new TarefaAcessaBancoProcedimento(pool, tx)).start();

    }
}