import java.net.ServerSocket;  
import java.net.Socket;  
import java.io.ObjectInputStream;    


public class ConfirmationResponseReceiver implements Runnable{
	
	private ServerSocket ss;

	public void run() {

		try {
			
			ss = new ServerSocket(9192);  

			while(true) {  

				//System.out.println("Esperando por arquivos."); 
				//ss.setSoTimeout(10000);
				//Socket socket = null;
				Socket socket = ss.accept(); 

				ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  
				String resposta = in.readUTF();  

				System.out.println(resposta);
			}  
		} catch (Exception ex) {  
			ex.printStackTrace();  
		}  

	}  
}
