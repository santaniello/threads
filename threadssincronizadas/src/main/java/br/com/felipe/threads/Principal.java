package br.com.felipe.threads;

public class Principal {

    public static void main(String[] args) {

        Banheiro banheiro = new Banheiro();

        //Passando a tarefa e o nome do Thread
        Thread convidado1 = new Thread(new TarefaNumero1(banheiro), "João");
        convidado1.start();
        Thread convidado2 = new Thread(new TarefaNumero2(banheiro), "Pedro");
        convidado2.start();
        Thread limpeza = new Thread(new TarefaLimpeza(banheiro), "Limpeza");
        /*Faz todo sentido e podemos dar uma dica para o escalonador, definindo que há threads com uma prioridade maior do que outras. Isso é apenas uma dica e não temos garantias, mas muito provável que o escalonador respeite a dica.*/
        limpeza.setPriority(Thread.MAX_PRIORITY);
        /*Você pode definir que threads dependem de outros. Em outras palavras, há threads que só deveriam rodar enquanto outros existem. Queremos que a máquina virtual automaticamente termine o thread de limpeza quando não existe mais nenhum convidado. Esse tipo de thread se chama Daemon.*/
        limpeza.setDaemon(true);
        limpeza.start();
    }
}