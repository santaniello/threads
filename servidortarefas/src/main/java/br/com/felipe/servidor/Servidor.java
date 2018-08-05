package br.com.felipe.servidor;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Servidor {
   private ServerSocket servidor;
   private ExecutorService threadPool;
   private AtomicBoolean isRunning;
   private BlockingQueue<String> filaComandos;


   public Servidor(int port){
       try {
           System.out.println("---- Iniciando Servidor ----");
           this.servidor = new ServerSocket(port);
           this.threadPool = Executors.newCachedThreadPool(new FabricaDeThreads());
           isRunning = new AtomicBoolean(true);
           this.filaComandos = new ArrayBlockingQueue<>(2);
           iniciarConsumidores();
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   public void start(){
       while (isRunning.get()) {
           try {
               Socket socket = this.servidor.accept();
               System.out.println("Aceitando novo cliente na porta " + socket.getPort());
               // Para cada cliente, criamos uma thread separada (e reaproveitamos do pool de thread) ...
               threadPool.execute(new DistribuirTarefas(threadPool,filaComandos,socket,this));
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

   public void stop(){
       try {
           this.servidor.close();
           this.threadPool.shutdown();
           this.isRunning.set(false);
       } catch (IOException e) {
           e.printStackTrace();
       }
   }

   // na classe ServidorTarefas
    private void iniciarConsumidores() {
        int qtdConsumidores = 2;
        for (int i = 0; i < qtdConsumidores; i++) {
            TarefaConsumir tarefa = new TarefaConsumir(filaComandos);
            this.threadPool.execute(tarefa);
        }
    }

}
