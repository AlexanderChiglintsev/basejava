import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MainConcurrency {
    private static final int THREADS_NUMBER = 10000;
    private static int counter;
    private final AtomicInteger atomicCounter = new AtomicInteger();
    private static final Lock lock = new ReentrantLock();

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

        CountDownLatch latch = new CountDownLatch(THREADS_NUMBER);
        //List<Thread> threads = new ArrayList<>(THREADS_NUMBER);

        ExecutorService executorService = Executors.newCachedThreadPool();
        //CompletionService completionService = new ExecutorCompletionService(executorService);
        final MainConcurrency mainConcurrency = new MainConcurrency();
        for (int i = 0; i < THREADS_NUMBER; i++) {

            executorService.submit(() -> {
                for (int j = 0; j < 1000; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });

            /*Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000; j++) {
                    mainConcurrency.inc();
                }
                latch.countDown();
            });
            thread.start();
            threads.add(thread);*/
        }

        /*threads.forEach(t -> {
            try {
                t.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });*/

        try {
            latch.await(10, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        executorService.shutdown();
        System.out.println(mainConcurrency.atomicCounter.get());
    }

    private void inc() {
        lock.lock();
        try {
            atomicCounter.incrementAndGet();
        } finally {
            lock.unlock();
        }

    }

}
