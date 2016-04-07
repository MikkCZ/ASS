package ass.stankmic;

import java.io.File;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class FileINode implements INode {

    private final File FILE;

    public FileINode(File FILE) {
        this.FILE = FILE;
    }

    public long size() {
        return FILE.length();
    }

}
