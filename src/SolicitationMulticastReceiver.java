import java.io.*;
import java.net.*;
public class SolicitationMulticastReceiver implements Runnable {


	public void run() {

		MulticastSocket socket = null;
		DatagramPacket inPacket = null;

		File diretorioPadrao = null;

		String[] listaDeArquivos = null;

		boolean arquivoEncontrado = false;

		byte[] inBuf = new byte[256];

		try {
			//Prepare to join multicast group
			socket = new MulticastSocket(9999);
			InetAddress address = InetAddress.getByName("224.2.2.8");
			socket.joinGroup(address);

			diretorioPadrao = new File("C:/TRANSFERENCIA/");
			//verifica se o diretorio existe, caso sim crie uma lista com os arquivos contidos 
			if(diretorioPadrao.isDirectory()) {
				System.out.println("Sim, " + diretorioPadrao.getAbsolutePath() + " é um diretório" + "\n");
				listaDeArquivos = diretorioPadrao.list();
				if(listaDeArquivos.length > 0) {
					for(int i=0; i<listaDeArquivos.length; i++)
						System.out.println(listaDeArquivos[i]);
				}
			}

			while (true) {
				inPacket = new DatagramPacket(inBuf, inBuf.length);
				socket.receive(inPacket);
				String msg = new String(inBuf, 0, inPacket.getLength());
				
				//caso a mensagem recebida seja da prórpia máquina, então ignorar e reiniciar o laço
				if(inPacket.getAddress().toString().contains(InetAddress.getLocalHost().getHostAddress().toString()))	
					continue;
					
				System.out.println("" + inPacket.getAddress() + " Solicitou o arquivo: " + msg);

				for(int i=0; i<listaDeArquivos.length; i++) {
					if(listaDeArquivos[i].equals(msg)){
						arquivoEncontrado = true;
						break;
					} 
				}

				if(arquivoEncontrado) {
					System.out.println("O arquivo " + msg + " foi encontrado !!!!!!" + "\n");
					//respondaMulticast("Eu, máquina " + InetAddress.getLocalHost().getHostAddress().toString() + ", possuo o arquivo " + msg + "\n");
					//confirmationSenderSocket("Eu, máquina " + InetAddress.getLocalHost().getHostAddress().toString() + ", possuo o arquivo " + msg + "\n");
					try {
						respondaPosseArquivoSocket(inPacket.getAddress());
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
				else if(!inPacket.getAddress().toString().contains(InetAddress.getLocalHost().getHostAddress().toString())){
					System.out.println("O arquivo " + msg + " não foi encontrado." + "\n");
				}

				arquivoEncontrado = false;

			}
		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}

	public void respondaPosseArquivoSocket(InetAddress inetAddress) throws Exception {

		try { 
			
			ConfirmationResponseSender confSnd = new ConfirmationResponseSender();
			Thread threadConfirmationSender = new Thread(confSnd);	
			confSnd.setMensagem("Eu possuo o arquivo");
			confSnd.setEnderecoDestino(inetAddress);
			threadConfirmationSender.start();
			
		} catch(Exception e) {  
			e.printStackTrace();  
			System.err.println("Erro ao confirmar a posse do arquivo");
		} 
	}  


	public void respondaMulticast(String resposta) {

		DatagramSocket socket = null;
		DatagramPacket outPacket = null;

		byte[] outBuf;
		final int PORT = 9999;

		try {
			socket = new DatagramSocket();

			outBuf = resposta.getBytes();

			//Send to multicast IP address and port
			InetAddress address = InetAddress.getByName("224.2.2.8");
			outPacket = new DatagramPacket(outBuf, outBuf.length, address, PORT);

			socket.send(outPacket);

		} catch (IOException ioe) {
			System.out.println(ioe);
		}

	}
}