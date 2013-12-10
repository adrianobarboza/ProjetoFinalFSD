import java.util.Scanner;


public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		String diretorio = "C:/TRANSFERENCIA/";
		String resposta = null;
		Scanner entrada = new Scanner (System.in); 
		
		System.out.println("Deseja utilizar o diretorio padr�o " + diretorio + " ? sim - nao");
		resposta = entrada.nextLine();
		if(resposta.contains("nao")){
			System.out.println("Digite o diret�rio desejado: ");
			diretorio = entrada.nextLine();
		}

		
		SolicitationMulticastReceiver multiCastRcv = new SolicitationMulticastReceiver(diretorio);
		Thread threadMultiCastRcv = new Thread(multiCastRcv);
		threadMultiCastRcv.start();
		
		Thread.sleep(1000);
		
		SolicitationMulticastSender multiCastSnd = new SolicitationMulticastSender();
		Thread threadMultiCastSnd = new Thread(multiCastSnd);
		threadMultiCastSnd.start();
		
		
	}

}
