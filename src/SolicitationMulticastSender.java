import java.io.*;
import java.net.*;
import java.util.Scanner;

public class SolicitationMulticastSender implements Runnable {

	public void run() {

		Scanner entrada = new Scanner (System.in); 

		DatagramSocket socket = null;
		DatagramPacket outPacket = null;

		byte[] outBuf;
		final int PORT = 9999;

		try {
			socket = new DatagramSocket();
			String solicitacao;
			String nick;

			System.out.println("\n"+"Qual o seu nome?");
			nick = entrada.nextLine();
			System.out.println("\nBem vindo ao console de solicitação de arquivos, " + nick);
			System.out.println();

			while (true) {
				solicitacao = entrada.nextLine();
				
				solicitacao = cifraSaida(solicitacao, 1, 2);

				outBuf = solicitacao.getBytes();

				//Send to multicast IP address and port
				InetAddress address = InetAddress.getByName("224.2.2.8");
				outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);
				
				socket.send(outPacket);
				
				esperaRecebimentoResposta();

			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}
	}

	private void esperaRecebimentoResposta() {
		
		try { 
			SocketMessageReceiver confRcv = new SocketMessageReceiver();
			Thread threadConfirmationReceiver = new Thread(confRcv);	
			threadConfirmationReceiver.start();
		} catch (Exception e) {
			System.err.println("Erro ao esperar o recebimento de confirmação de posse de arquivo\n");
		}
			
	}
	
	public static String cifraSaida(String str, int chavePar, int chaveImpar){
		
		StringBuilder builder = new StringBuilder();
		char c;
        for (int i = 0; i < str.length(); i++) {
        	
        	//caso seja uma posição par a chave será diferente da impar
        	if(i==0 || i%2==0){
        		c = (char)(str.charAt(i) + chavePar);
        	} 
        	//caso seja uma posição ímpar a chave será diferente da par
        	else{
        		c = (char)(str.charAt(i) + chaveImpar);
        	}
        	//anexar a letra cifrada a string
        	builder.append(c);
        }
        
        //retorna a string
        return builder.toString();
		
	}
}