import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;


public class FileReceiver implements Runnable{
	
	private ServerSocket ss;

	public void run(){
		
		try {  
			ss = new ServerSocket(9292); 
			ss.setSoTimeout(8000);
			System.out.println("Esperando por arquivos.");  
			Socket socket = ss.accept();  
			ObjectInputStream in = new ObjectInputStream(socket.getInputStream());  
			String fileName = in.readUTF();  
			long size = in.readLong();  

			System.out.println("Processando arquivo: " + fileName + " - " + size + " bytes.");  

			//FileOutputStream fos = new FileOutputStream("c:/TRANSFERENCIA/" + fileName); 
			FileOutputStream fos = new FileOutputStream(Main.diretorio + fileName);
			byte[] buf = new byte[(int)size];  
			
			while(true) { 

				int len = in.read(buf);  
				if(len == -1) break;  
				fos.write(buf, 0, len); 

			}  
			fos.flush();  
			fos.close();  
			System.out.println("Pronto. Arquivo recebido.");  

		} catch (Exception ex) {  
			System.err.println("O servidor não enviou o arquivo"); 
		}  finally {
			try {
				this.ss.close();
			} catch (IOException e) {
				System.err.println("Não foi possível fechar o socket do file receiver");
			}
		}
	}

}
