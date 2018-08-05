package br.com.felipe.cliente;

public class ClienteTarefas {

	public static void main(String[] args) throws Exception {
		Cliente cliente = new Cliente("localhost",12344);
		cliente.communicatesWithServer();
	}
}
