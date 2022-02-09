package MyMonitor;



public class TheMonitor implements Runnable{

	  public static Pauser pauser;

	  public TheMonitor(Pauser pauser){
	     this.pauser=pauser;
	   }

	  public void run(){
	    while(true){
	       try {
			pauser.look();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    }
	  }
}

//MyMonitor.pauser.look();