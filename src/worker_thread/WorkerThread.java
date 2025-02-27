package worker_thread;

import java.util.LinkedList;

public class WorkerThread {

    public static void main(String[] args) {
        BlockingQueue queue = new BlockingQueue();

        Thread worker = new Thread(() -> {
            while (true) {
                Runnable task = queue.get();
                task.run();
            }
        });
        worker.start();

        for (int i = 0; i < 10; i++) {
            queue.put(getTask());
        }
    }

    private static class BlockingQueue {
        LinkedList<Runnable> tasks = new LinkedList<>();

        public synchronized Runnable get() {
            while (tasks.isEmpty()) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Runnable task = tasks.get(0);
            tasks.removeFirst();
            return task;
        }

        public synchronized void put(Runnable task) {
            tasks.add(task);
            notify();
        }
    }

    private static Runnable getTask() {
        return new Runnable() {
            @Override
            public void run() {
                System.out.println("Task started: " + this);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Task finished: " + this);
            }
        };
    }
}
