
package br.com.felipe.threads;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigInteger;

import javax.swing.JLabel;
import javax.swing.JTextField;

public class AcaoBotao implements ActionListener {

	private JTextField primeiro;
	private JTextField segundo;
	private JLabel resultado;

	public AcaoBotao(JTextField primeiro, JTextField segundo, JLabel resultado) {
		this.primeiro = primeiro;
		this.segundo = segundo;
		this.resultado = resultado;
	}


	public void actionPerformed(ActionEvent e) {
		Thread threadCalculo = new Thread(new TarefaMultiplicacao(primeiro,segundo,resultado),"Thread Calculador");
		threadCalculo.start();




	}

}