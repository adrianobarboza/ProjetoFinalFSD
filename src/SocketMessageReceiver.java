import java.net.ServerSocket;  
import java.net.Socket;  
import java.io.IOException;
//import java.io.ObjectInputStream;    


public class SocketMessageReceiver implements Runnable{

	private ServerSocket ss;
	private Socket socket;

	public void run() {

		try {

			ss = new ServerSocket(9192);  
			ss.setSoTimeout(2000);
			socket = ss.accept(); 

			//ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  
			//long type = in.readLong();  
			
			imprimePossuiArquivo();
			recebeArquivo();

		} catch (Exception ex) {  
			System.err.println("N�o foi obtida nenhuma mensagem de confirma��o");
		}  finally {
			try {
				this.ss.close();
			} catch (IOException e) {
				System.err.println("N�o foi poss�vel fechar o socket de receber a mensagem de confirma��o");
			}
		}

	}

	private void imprimePossuiArquivo() {
		System.out.println("O n� " + socket.getInetAddress() + " possui o arquivo");	
	}  
	
	private void recebeArquivo() {
		try {
			FileReceiver fileRcv = new FileReceiver();
			Thread threadFileReceiver = new Thread (fileRcv);
			threadFileReceiver.start();
		} catch(Exception e) {  
			System.err.println("Erro ao tentar receber o arquivo.");
		} 
		
	}
}
