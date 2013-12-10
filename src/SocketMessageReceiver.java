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
			System.err.println("Não foi obtida nenhuma mensagem de confirmação");
		}  finally {
			try {
				this.ss.close();
			} catch (IOException e) {
				System.err.println("Não foi possível fechar o socket de receber a mensagem de confirmação");
			}
		}

	}

	private void imprimePossuiArquivo() {
		System.out.println("O nó " + socket.getInetAddress() + " possui o arquivo");	
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
