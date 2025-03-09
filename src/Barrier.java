public class Barrier implements IBarrier {
    private final int maxThreads;
    private int threadsInCurrentWaitingPhase = 0;
    private int currentWaitingPhase = 1;

    public Barrier(int maxThreads) {
        this.maxThreads = maxThreads;
    }

    @Override
    public void await() throws InterruptedException {
        // Threads remain in await as long as their waitingPhase is active. A phase ends when either maxThreads many
        // threads have called await or freeAll is called.
        final int myPhase = currentWaitingPhase;
        ++threadsInCurrentWaitingPhase;
        if (threadsInCurrentWaitingPhase == maxThreads) {
            freeAll();
        }
        while (currentWaitingPhase == myPhase) {
            this.wait();
        }
    }

    @Override
    public void freeAll() {
        // By increasing currentWaitingPhase all threads leave the await method because their phase is smaller. then
        // all threads that call await after this freeAll will
        ++currentWaitingPhase;
        threadsInCurrentWaitingPhase = 0;
        this.notifyAll();

    }
}
