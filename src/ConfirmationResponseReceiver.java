import java.net.ServerSocket;  
import java.net.Socket;  
import java.io.IOException;
import java.io.ObjectInputStream;    


public class ConfirmationResponseReceiver implements Runnable{

	private ServerSocket ss;

	public void run() {

		try {

			ss = new ServerSocket(9192);  
			ss.setSoTimeout(2000);
			Socket socket = ss.accept(); 

			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  
			String resposta = in.readUTF();  

			System.out.println(resposta);

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
}
