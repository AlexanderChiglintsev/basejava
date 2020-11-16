import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static final Object LOCK1 = new Object();
    private static final Object LOCK2 = new Object();

    public static void main(String[] args) {

        System.out.println(Thread.currentThread().getName());
        final Thread thread0 = new Thread() {
            @Override
            public void run() {
                System.out.println(getName() + ", " + getState());
            }
        };
        thread0.start();

        new Thread(() -> System.out.println(Thread.currentThread().getName() + ", " + Thread.currentThread().getState())).start();
        System.out.println(thread0.getState());

        List<Thread> threads = new ArrayList<>(THREADS_NUMBER);
        final MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < THREADS_NUMBER; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    mainConcurrency.inc();
                }
            });
            thread.start();
            threads.add(thread);
        }

        threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(counter);

        System.out.println("\nDeadlock");

        Thread t1 = new Thread(MainConcurrency::first);
        Thread t2 = new Thread(MainConcurrency::second);
        t1.start();
        t2.start();

    }

    private synchronized void inc() {
        counter++;
    }

    private static void first() {
        synchronized (LOCK1){
            System.out.println("first()");
            Thread.yield();
            second();
        }
    }

    private static void second() {
        synchronized (LOCK2){
            System.out.println("second()");
            Thread.yield();
            first();
        }
    }

}
