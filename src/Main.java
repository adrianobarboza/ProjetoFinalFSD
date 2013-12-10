import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

	static String diretorio;
	private static ArrayList<String> listaDeAutorizados = new ArrayList<String>(); 

	public static void main(String[] args) throws InterruptedException {

		defineDiretorioPadrao();		

		leArquivoAutorizados();
		
		imprimeAutorizados();

		SolicitationMulticastReceiver multiCastRcv = new SolicitationMulticastReceiver(diretorio);
		Thread threadMultiCastRcv = new Thread(multiCastRcv);
		threadMultiCastRcv.start();

		Thread.sleep(500);

		SolicitationMulticastSender multiCastSnd = new SolicitationMulticastSender();
		Thread threadMultiCastSnd = new Thread(multiCastSnd);
		threadMultiCastSnd.start();

	}

	private static void defineDiretorioPadrao() {
		diretorio = "C:/TRANSFERENCIA/";
		String resposta = null;
		Scanner entrada = new Scanner (System.in); 

		System.out.println("Deseja utilizar o diretorio padrão " + diretorio + " ? sim - nao");
		resposta = entrada.nextLine();
		if(resposta.contains("nao")){
			System.out.println("Digite o diretório desejado: ");
			diretorio = entrada.nextLine();
		}
	}

	private static void imprimeAutorizados() {
		System.out.println("Nós autorizados: \n");
		for(int i = 0; i<listaDeAutorizados.size()-1; i++) {
			System.out.println(listaDeAutorizados.get(i));
		}
		System.out.println();
	}

	public static void leArquivoAutorizados() {
		try {
			FileReader arq = new FileReader("autorizados.txt");
			BufferedReader lerArq = new BufferedReader(arq);
			int i = 0; 
			
			listaDeAutorizados.clear();
			listaDeAutorizados.add(lerArq.readLine());
			while (listaDeAutorizados.get(i) != null) {
				listaDeAutorizados.add(lerArq.readLine()); 
				i++;
			}
			arq.close();
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n",
					e.getMessage());
		}
	}
	
	public static ArrayList<String> getListaDeAutorizados() {
		return listaDeAutorizados;
	}

}
