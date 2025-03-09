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
// Durch Erhöhen von currentWaitingPhase verlassen alle Threads die
// await−Methode , da ihre Phase kleiner ist . dann Alle Threads , die await
// nach diesem freeAll aufrufen ,
        ++currentWaitingPhase;
        threadsInCurrentWaitingPhase = 0;
        this.notifyAll();

    }
}
