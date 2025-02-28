package worker_thread;

import java.util.LinkedList;

public class WorkerThread {

    public static void main(String[] args) {
        workerThreadStart();
    }

    private static void workerThreadStart() {
        BlockingQueue queue = new BlockingQueue();

        Thread worker = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                Runnable task;
                try {
                    task = queue.get();
                    task.run();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        worker.start();

        for (int i = 0; i < 10; i++) {
            queue.put(getTask());
        }
    }

    private static class BlockingQueue {
        LinkedList<Runnable> tasks = new LinkedList<>();

        public synchronized Runnable get() throws InterruptedException {
            while (tasks.isEmpty()) {
                wait();
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
                    Thread.currentThread().interrupt();
                }
                System.out.println("Task finished: " + this);
            }
        };
    }
}
