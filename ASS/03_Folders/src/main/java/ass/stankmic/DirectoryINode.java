package ass.stankmic;

import java.io.File;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class DirectoryINode implements INode {

    private final File DIR;

    public DirectoryINode(File DIR) {
        this.DIR = DIR;
    }

    public long size() {
        long size = 0;
        File[] files = DIR.listFiles();
        if (files != null) {
            for (File f : files) {
                size += INodeFactory.newInstance(f).size();
            }
        }
        return size;
    }

}
