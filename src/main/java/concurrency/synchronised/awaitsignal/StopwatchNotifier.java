package concurrency.synchronised.awaitsignal;

/**
 * Callback for notifying the {@link StopwatchTimer}
 * reached its time limit.
 */
public interface StopwatchNotifier {

    void finished();
}
