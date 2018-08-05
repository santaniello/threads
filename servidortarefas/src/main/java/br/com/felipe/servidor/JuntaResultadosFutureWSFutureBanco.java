package br.com.felipe.servidor;

import java.io.PrintStream;
import java.util.concurrent.*;

public class JuntaResultadosFutureWSFutureBanco implements Callable<Void> {

    private Future<String> futureWS;
    private Future<String> futureBanco;
    private PrintStream saidaCliente;

    public JuntaResultadosFutureWSFutureBanco(Future<String> futureWS,
            Future<String> futureBanco, PrintStream saidaCliente) {
        this.futureWS = futureWS;
        this.futureBanco = futureBanco;
        this.saidaCliente = saidaCliente;
    }

    //não queremos devolver nada, então usamos um tipo que representa nada: Void
    public Void call() { 

        System.out.println("Aguardando resultados do future WS e Banco");

        try {
            /*
            * Dada uma Future, nós obtemos seu resultado através de uma chamada ao método get, este sim,
            * bloqueia a execução do nosso código até que o seja retornando o resultado da operação assíncrona
            * */
            String numeroMagico = this.futureWS.get(20, TimeUnit.SECONDS);
            String numeroMagico2 = this.futureBanco.get(20, TimeUnit.SECONDS);

            this.saidaCliente.println("Resultado do comando c2: "
                    + numeroMagico + ", " + numeroMagico2);

        } catch (InterruptedException | ExecutionException | TimeoutException e) {

            System.out.println("Timeout: Cancelando a execução do comando c2");

            this.saidaCliente.println("Timeout na execução do comando c2");
            this.futureWS.cancel(true);
            this.futureBanco.cancel(true);
        }

        System.out.println("Finalizou JuntaResultadosFutureWSFutureBanco");

        return null; //esse Callable não tem retorno, por isso null
    }
}