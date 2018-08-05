package br.com.felipe.cliente;

import java.io.IOException;
import java.net.Socket;

public class Cliente {
    private Socket socket;
    private String host;
    private int port;

    public Cliente(String host,int port){
        this.host = host;
        this.port = port;
    }

    private void start(){
        try {
            this.socket = new Socket(this.host, this.port);
            System.out.println("Conexão Estabelecida");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Método que envia comandos para o servidor...
     *
     * */
    public void communicatesWithServer(){
          start();
          receiveCommand();
          sendCommand();
          end();
    }

    /**
     * O método enviarComando.join() faz com que a thread que executa espere até o outro acabar (no caso enviarComando).
     *
     * Caso não fizessemos esse comando, o socket seria fechado (pois ele é o proximo
     * passo a ser executado logo, ele seria executado em paralelo) antes de enviar o comando e então um erro
     * estouraria no console...
     *
     * */
    private void sendCommand(){
        try {
            Thread enviarComando = new Thread(new ThreadEnviaComando(this.socket));
            enviarComando.start();
            enviarComando.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void receiveCommand(){
       Thread recebeResposta = new Thread(new ThreadRecebeResposta(this.socket));
       recebeResposta.start();
    }

    private void end(){
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
