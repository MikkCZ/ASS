package ass.stankmic;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author Michal Stanke <stankmic@fel.cvut.cz>
 */
public class FileINodeTest {

    File tempDir, tempSubDir, tempFile;
    int tempDirSize, tempSubDirSize;
    final int tempFileSize = 607;
    INode TESTED;

    @Before
    public void setUp() throws IOException {
        // create tempDir and tempSubDir inside
        try {
            tempDir = Files.createTempDirectory(null).toFile();
            tempSubDir = new File(tempDir.toString() + "/tempSubDir");
            tempSubDir.mkdirs();
        } catch (IOException ex) {
            Logger.getLogger(FileINodeTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        // create one tempFile in tempDir
        tempFile = createTempFile(tempDir.toPath(), "tempFile", tempFileSize);
        tempDirSize += tempFileSize;
        // create more temp files in tempDir
        int[] tempDirFileSizes = {35, 65, 908, 1231, 23};
        for (int fileSize : tempDirFileSizes) {
            try {
                createTempFile(tempDir.toPath(), "file" + fileSize, fileSize);
            } catch (IOException ex) {
                continue;
            }
            tempDirSize += fileSize;
        }
        // create more temp files in tempSubDir
        int[] tempSubDirFileSizes = {55, 2342, 7, 45, 8, 1, 5567};
        for (int fileSize : tempSubDirFileSizes) {
            try {
                createTempFile(tempSubDir.toPath(), "file" + fileSize, fileSize);
            } catch (IOException ex) {
                continue;
            }
            tempSubDirSize += fileSize;
        }
        tempDirSize += tempSubDirSize;
    }

    private File createTempFile(Path path, String name, int size) throws IOException {
        FileOutputStream stream = new FileOutputStream(path.toString() + "/" + name);
        byte[] buffer = new byte[size];
        stream.write(buffer);
        stream.flush();
        stream.close();
        return new File(path.toString() + "/" + name);
    }

    @After
    public void tearDown() {
        deleteFolderRecursively(tempDir);
        tempDir = null;
        tempDirSize = 0;
        tempSubDir = null;
        tempSubDirSize = 0;
        tempFile = null;
        TESTED = null;
    }

    private void deleteFolderRecursively(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isDirectory()) {
                    deleteFolderRecursively(f);
                } else {
                    f.delete();
                }
            }
        }
        folder.delete();
    }

    @Test
    public void testOneFile() {
        TESTED = INodeFactory.newInstance(tempFile);
        assertEquals("The size of one file is not computed correctly.", tempFileSize, TESTED.size());
    }

    @Test
    public void testFolderWithoutSubdirectories() {
        TESTED = INodeFactory.newInstance(tempSubDir);
        assertEquals("The size of directory with no subdirectories is not computed correctly.", tempSubDirSize, TESTED.size());
    }

    @Test
    public void testFolderWithSubdirectory() {
        TESTED = INodeFactory.newInstance(tempDir);
        assertEquals("The size of directory with a subdirectory is not computed correctly.", tempDirSize, TESTED.size());
    }

}
