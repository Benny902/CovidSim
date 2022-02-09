package ZzDeleteThis;
import java.time.LocalTime;
import java.util.Random;


public class HorseRace implements Runnable {
    public final String id;  
    private int distance;

    public HorseRace(String id, int distance) {
        this.id = id; 
        this.distance = distance;
    }

    @Override
    public void run() {
        Random rnd = new Random();
        while (this.distance > 0) {
            this.distance -= (1 + rnd.nextInt(9));
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //
            }
        }
        System.out.println("in while loop " +Thread.currentThread().getId());
        System.out.printf("[%s] %s%n", LocalTime.now(), this.id);
    }

    
    public static void main(String[] args) {
        
        Thread[] threads = new Thread[10];

        for (int i = 0; i < threads.length; i++) // init all horses
            threads[i] = new Thread(new HorseRace("Horse " + i, 30));

        System.out.printf("[%s] Race Started%n", LocalTime.now());
       
        for (Thread thread : threads) // start all horses
            thread.start();

        for (Thread thread : threads) { // wait for all of them to finish
            try {
                thread.join();
            } catch (InterruptedException e) {

            }
        }
        System.out.printf("[%s] Race finished%n", LocalTime.now());
        
    }
    
    
}