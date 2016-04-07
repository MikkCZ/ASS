package ass.stankmic;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 * @param <T>
 */
public class FutureImpl<T> implements Future<T> {

    private Exception ex;
    private T result;

    public synchronized T get() throws Exception {
        while(ex == null && result == null) {
            wait();
        }
        if(ex != null) {
            throw ex;
        }
        return result;
    }
    
    protected synchronized void setException(Exception ex) {
        this.ex = ex;
        notifyAll();
    }

    protected synchronized void setResult(T result) {
        this.result = result;
        notifyAll();
    }

}
