package MyMonitor;

public class Pauser{

	  private boolean isPaused=false;

	  public synchronized void pause(){
	     isPaused=true;
	    }

	  public synchronized void resume(){
	    isPaused=false;
	    notifyAll();
	  }

	  public synchronized void look() throws InterruptedException{
	    while(isPaused){
	        wait();
	      }
	   }

}

  /*
public class Caller{

  Pauser pauser=new Pauser();
  for(int i = 0; i<1000;i++)
    new Thread(new MyRunnable(pauser)).start();

//pause all threads
pauser.pause();

//resume all threads
pauser.resume();
}
  */