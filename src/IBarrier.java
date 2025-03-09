public interface IBarrier {
    void await() throws InterruptedException;
    void freeAll();
}
