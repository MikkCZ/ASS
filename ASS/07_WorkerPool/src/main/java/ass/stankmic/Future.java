package ass.stankmic;

public interface Future<T> {

    T get() throws Exception;
}
