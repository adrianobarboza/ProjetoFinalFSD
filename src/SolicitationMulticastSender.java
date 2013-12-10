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

			System.out.println("\n"+"Qual o seu nick?");
			nick = entrada.nextLine();
			System.out.println("Bem vindo ao chat, " + nick);
			System.out.println();

			while (true) {
				solicitacao = entrada.nextLine();

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
			ConfirmationResponseReceiver confRcv = new ConfirmationResponseReceiver();
			Thread threadConfirmationReceiver = new Thread(confRcv);	
			threadConfirmationReceiver.start();
		} catch (Exception e) {
			e.printStackTrace();  
			System.err.println("Erro ao esperar o recebimento de confirmação de posse de arquivo");
		}
			
	}
}