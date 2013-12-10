import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class ConfirmationResponseSender implements Runnable {

	private String mensagem = new String();
	private InetAddress enderecoDestino;

	public void run() {
		
		try { 

			Socket socket = new Socket(enderecoDestino, 9192);  
			ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());  
			out.writeUTF(mensagem);   
 
			out.close();  
			socket.close();  
 
		} catch(Exception e) {   
			System.err.println("O servidor que solicitou o arquivo n�o est� dispon�vel para recebimento da confirma��o");
		}  
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public void setEnderecoDestino(InetAddress enderecoDestino) {
		this.enderecoDestino = enderecoDestino;
	}
	
	
}