package br.com.felipe.cliente;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * Classe que captura comandos do teclado e envia para o servidor.
 *
 * Explicações: Através da classe Scanner, nós capturamos os comandos do teclado (System.in) e enviamos para o servidor
 * através de um PrintStream(sockect.getOutPutStream())
 *
 * OBS: Quando queremos enviar dados para o servidor, usamos socket.getOutPutStream();
 *      Quando queremos receber dados do servidor, usamos socket.getInputStream();
 *
 * */
public class ThreadEnviaComando  implements  Runnable {

    private Socket socket;

    public ThreadEnviaComando(Socket socket){
        this.socket = socket;
    }

    @Override
    public void run() {
        // Usamos o OutputStream para enviar os dados/comandos para o servidor através do socket.
        try {
            PrintStream saida = new PrintStream(this.socket.getOutputStream());
            // Aguardando enter
            Scanner teclado = new Scanner(System.in);
            while (teclado.hasNextLine()) {
                String linha = teclado.nextLine();
                // sai do laço se o usuário digitar enter...
                if (linha.trim().equals("")) {
                    break;
                }
                saida.println(linha);
            }
            saida.close();
            teclado.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
