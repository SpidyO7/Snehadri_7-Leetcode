class Foo {
    private int state = 1;

    public Foo() {
    }

    public synchronized void first(Runnable printFirst) throws InterruptedException {
        printFirst.run();
        state = 2;
        notifyAll();
    }

    public synchronized void second(Runnable printSecond) throws InterruptedException {
        while (state != 2) {
            wait();
        }

        printSecond.run();
        state = 3;
        notifyAll();
    }

    public synchronized void third(Runnable printThird) throws InterruptedException {
        while (state != 3) {
            wait();
        }

        printThird.run();
    }
}