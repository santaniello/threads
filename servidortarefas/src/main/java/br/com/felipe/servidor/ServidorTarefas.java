package br.com.felipe.servidor;

public class ServidorTarefas {

	public static void main(String[] args) throws Exception {
		Servidor servidor = new Servidor(12344);
		servidor.start();
	}

}
