package edu.br.sd.nomeacao.servidor1;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.InputMismatchException;

import edu.br.sd.nomeacao.mensagem.Mensagem;

public class Servidor1 {
	
	
    public static boolean isCPF(String CPF) {
        // analisar erros com sequencias de CPF
        if (CPF.equals("00000000000") ||
            CPF.equals("11111111111") ||
            CPF.equals("22222222222") || CPF.equals("33333333333") ||
            CPF.equals("44444444444") || CPF.equals("55555555555") ||
            CPF.equals("66666666666") || CPF.equals("77777777777") ||
            CPF.equals("88888888888") || CPF.equals("99999999999") ||
            (CPF.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        //verificação de erros na entrada
        try {
      
        	// Calculo do 1 digito verificador
            sm = 0;
            peso = 10;
            for (i=0; i<9; i++) {
        
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                dig10 = '0';
            else dig10 = (char)(r + 48);

            //Calculo do do 2 digito verificador
            sm = 0;
            peso = 11;
            for(i=0; i<10; i++) {
            num = (int)(CPF.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
            }

            r = 11 - (sm % 11);
            if ((r == 10) || (r == 11))
                 dig11 = '0';
            else dig11 = (char)(r + 48);

            //Verifica se os digitos calculados conferem com os digitos informados.
            if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
                 return(true);
            else return(false);
                } catch (InputMismatchException erro) {
                return(false);
            }
        }

    public static String imprimirCPF(String CPF) {
        return(CPF.substring(0, 3) + "." + CPF.substring(3, 6) + "." +
        CPF.substring(6, 9) + "-" + CPF.substring(9, 11));
    }
	
    
    public static void envioRegistro(String value) {
		try {
			//Receber nome do serviço e servidor
			Mensagem msg = new Mensagem(value);
			msg.setEndereco("localhost:12345");
			msg.setServico("validarcpf");
			
			//Criacao do socket para o servidor 1
			Socket cliente = new Socket("localhost", 3000);
			ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
			ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
			saida.writeObject(msg);
			saida.flush();
			String msgReply = entrada.readUTF();
			System.out.println("Mensagem Recebida: "+ msgReply);
			entrada.close();
			saida.close();
			cliente.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    	
    }
    
    public static void main(String[] args) {

		
		
		ServerSocket servidorCPF;	
		try {
				envioRegistro("registro");
				
				
				servidorCPF = new ServerSocket(12345);
				System.out.println("Ouvindo na porta: 12345");
				
			    //loop para detectar entradas dos usuários
				while(true) {
			    	
			    	System.out.println("Aguardando Clientes . . . ");
			    	Socket cliente = servidorCPF.accept();
			        System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
			        ObjectOutputStream saida = new ObjectOutputStream(cliente.getOutputStream());
			        ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
			        String CPF = entrada.readUTF();
			        
			        try{
			        	isCPF(CPF);
			        	saida.writeUTF("CPF — " + imprimirCPF(CPF));
			        	saida.flush();
			        	System.out.println("Conexão finalizada.");
				        saida.close();
				        entrada.close();
				        cliente.close();
			       
			        } catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
			    }
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

	}

}
