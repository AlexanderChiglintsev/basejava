class Deadlock {

    private final Object LOCK1;
    private final Object LOCK2;

    private Deadlock(Object LOCK1, Object LOCK2) {
        this.LOCK1 = LOCK1;
        this.LOCK2 = LOCK2;
    }

    public static void main(String[] args) {
        System.out.println("\nDeadlock\n");

        final Object lock1 = new Object();
        final Object lock2 = new Object();
        Deadlock dl1 = new Deadlock(lock1, lock2);
        Deadlock dl2 = new Deadlock(lock2, lock1);

        Thread t1 = new Thread(dl1::lock);
        Thread t2 = new Thread(dl2::lock);

        new Thread(() -> {
            int i = 0;
            while (i < 5) {
                print(t1);
                print(t2);
                i++;
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();


        t1.start();
        t2.start();
    }

    private void lock() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (LOCK1) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            synchronized (LOCK2) {
                System.out.println("Not printed.");
            }
        }
    }

    private static void print(Thread thread) {
        System.out.println("Thread: " + thread.getName() + " with state: " + thread.getState());
    }

}
