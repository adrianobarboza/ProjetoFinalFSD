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
			System.err.println("Não foi obtida nenhuma mensagem de confirmação");
		}  finally {
			try {
				this.ss.close();
			} catch (IOException e) {
				System.err.println("Não foi possível fechar o socket de receber a mensagem de confirmação");
			}
		}

	}  
}
