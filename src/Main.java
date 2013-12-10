
public class Main {
	
	public static void main(String[] args) throws InterruptedException {
		
		SolicitationMulticastReceiver multiCastRcv = new SolicitationMulticastReceiver();
		Thread threadMultiCastRcv = new Thread(multiCastRcv);
		threadMultiCastRcv.start();
		
		Thread.sleep(1000);
		
		SolicitationMulticastSender multiCastSnd = new SolicitationMulticastSender();
		Thread threadMultiCastSnd = new Thread(multiCastSnd);
		threadMultiCastSnd.start();
		
		
	}

}
