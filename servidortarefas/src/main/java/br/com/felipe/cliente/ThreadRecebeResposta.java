package br.com.felipe.cliente;

import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe que recebe  comandos servidor.
 *
 * Explicações: Através da classe Scanner(socket.getInputStream()), nós capturamos os comandos do vindos do servidor
 * através do socket socket.getInputStream()
 *
 * OBS: Quando queremos enviar dados para o servidor, usamos socket.getOutPutStream();
 *      Quando queremos receber dados do servidor, usamos socket.getInputStream();
 *
 * */
public class ThreadRecebeResposta implements  Runnable {

    private Socket socket;

    public ThreadRecebeResposta(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            System.out.println("Recebendo dados do servidor");
            Scanner respostaServidor = new Scanner(socket.getInputStream());
            while (respostaServidor.hasNextLine()) {
                String linha = respostaServidor.nextLine();
                System.out.println(linha);
            }
            System.out.println("Encerrando comunicação com o servidor...");
            respostaServidor.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
