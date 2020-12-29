package edu.br.sd.nomeacao.client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import edu.br.sd.nomeacao.mensagem.Mensagem;

public class Cliente {
		
	public static String lookup(String value) {
		String endereco = "";
		Mensagem msg = new Mensagem(value);
		msg.setServico("validarcpf");
		try {
			Socket client = new Socket("localhost", 5000);
			ObjectOutputStream saida = new ObjectOutputStream(client.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(client.getInputStream());
			
			saida.writeObject(msg);
			saida.flush();
			String msgReply = entrada.readUTF();
			endereco = msgReply;
			System.out.println("Mensagem Recebida: "+ msgReply);
			entrada.close();
			saida.close();
			client.close();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return endereco;
	}
	
	
	public static void main(String[] args) {
		String adress = lookup("lookup");
		String[] strings = adress.split("\\:");
		String endereco = strings[0];
		int port = Integer.parseInt(strings[1]);
		
		try {
			
			//socket para receber informações do cliente 
			Socket nomeCliente = new Socket(endereco, port);
			ObjectOutputStream output = new ObjectOutputStream(nomeCliente.getOutputStream());
			ObjectInputStream input = new ObjectInputStream(nomeCliente.getInputStream());
			
			//Alterar valor antes do teste
			String msg = "00000000000";
			output.writeUTF(msg);
			output.flush();
			String msgReply = input.readUTF();
			
			System.out.println("Mensagem Recebida: "+ msgReply);
			input.close();
			output.close();
			nomeCliente.close();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
