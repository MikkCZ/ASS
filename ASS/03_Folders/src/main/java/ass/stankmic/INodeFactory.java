package ass.stankmic;

import java.io.File;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
final public class INodeFactory {

    private INodeFactory() {
    }

    public static INode newInstance(File path) {
        if (path.isDirectory()) {
            return new DirectoryINode(path);
        } else {
            return new FileINode(path);
        }
    }
}
