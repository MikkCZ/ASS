package ass.stankmic;

import javax.annotation.Nonnull;

public interface CloneableObject<T> extends Cloneable {

    @Nonnull T clone();

}
