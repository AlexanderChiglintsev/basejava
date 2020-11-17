import java.util.ArrayList;
import java.util.List;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private static int step;

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

        System.out.println("\nDeadlock\n");

        MainConcurrency mc1 = new MainConcurrency();
        MainConcurrency mc2 = new MainConcurrency();

        Thread t1 = new Thread(() -> mc1.doSomething(mc2));
        Thread t2 = new Thread(() -> mc2.doSomething(mc1));
        t1.start();
        t2.start();
    }

    private synchronized void inc() {
        counter++;
    }

    private synchronized void doSomething(MainConcurrency mc) {
        System.out.println(Thread.currentThread().getName());
        step++;
        Thread.yield();
        if ((step > 2)) {
            Thread.currentThread().interrupt();
        } else {
            mc.doSomething(mc);
        }
    }

}
