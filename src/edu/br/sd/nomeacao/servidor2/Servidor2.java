package edu.br.sd.nomeacao.servidor2;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import edu.br.sd.nomeacao.mensagem.Mensagem;

public class Servidor2 {

	public static void main(String[] args) throws ClassNotFoundException {
		//criação do socket
		ServerSocket serverSocket;
		Mensagem msg;
		
		//criacao do hash 
		Map<String, String> data = new HashMap<String, String>();
		
		try {
			serverSocket = new ServerSocket(5000);
			System.out.println("Conectando");
			while (true) {
				System.out.println("Aguardando novo Cliente...");
				Socket cliente = serverSocket.accept();
				System.out.println("Cliente Conectado: " + cliente.getInetAddress().getHostAddress());
				
				ObjectOutputStream output = new ObjectOutputStream(cliente.getOutputStream());
		        ObjectInputStream input = new ObjectInputStream(cliente.getInputStream());
		       
		        msg = (Mensagem)input.readObject();
		        switch (msg.getOperacao()) 
		        
		        {
				
		        //set para o serviço desejado
		        case "lookup": {
			        System.out.println("Enviando os dados sobre o cliente de nome: "+ msg.getServico());
		        	output.writeUTF(data.get(msg.getServico()));
		        	output.flush();
			        output.close();
			        input.close();
			        cliente.close();

			        
			        break;
			        
			    //set de registro do cadastro
				} case "registro":{
		        	data.put(msg.getServico(), msg.getEndereco());
			        System.out.println("Cadastro realizado!");
			        
			        output.writeUTF("Cadastro realizado!");
			        output.flush();
			        output.close();
			        
			        input.close();
			        
			        cliente.close();
			        
			        break;
				}
				
				}
			}
			
		} 
		catch (IOException e) {
			e.printStackTrace();
		}

	}

}