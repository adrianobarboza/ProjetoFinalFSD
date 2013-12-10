import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;


public class FileSender implements Runnable{

	private String fileName;
	private InetAddress endereco;

	public void run(){

		try {  
			
			File f = new File("C:/TRANSFERENCIA/" + fileName);  
			Socket socket = new Socket(endereco, 9292);
			//		/Socket socket = new Socket("192.168.254.12", 9292);  
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());  
			System.out.println("Transferindo o arquivo: " + f.getName());  
			out.writeUTF(f.getName());  
			out.writeLong(f.length());  
			FileInputStream in = new FileInputStream(f);  
			byte[] buf = new byte[(int)f.length()];  

			while(true) {  
				int len = in.read(buf);  
				if(len == -1) break;  
				out.write(buf, 0, len);   
			}  
			out.close();  
			socket.close(); 
			in.close();
			System.out.println("Pronto.");  
		} catch(Exception e) {  
			e.printStackTrace();  
		}  
	}
	
	
	
	public FileSender(InetAddress endereco, String fileName) {
		this.endereco = endereco;
	}



	@SuppressWarnings("unused")
	private FileSender(){}

}
