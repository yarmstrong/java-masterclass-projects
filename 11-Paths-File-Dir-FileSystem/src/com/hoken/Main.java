package com.hoken;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Main {
    public static void main(String[] args) {
        // playingWithPaths(); // structuring the pathname of a file or directory
        // playingWithFiles(); // copy, rename, move files
        // gettingFileMetadata(); // either u get metadata from Files.method() or using BasicFileAttributes type
        // readingDirectoryDirectDescendants();
        // systemSeparatorsFileStoresTempFileDir();
        // traverseEntireDir();
        copyEntireDir();
    }

    private static void copyEntireDir() {
        /* task is to copy entire Dir2 into existing Dir4, all logic is handled in the CopyFiles class */
        // try to delete Dir4/Dir2_copy @ 1st run
        Path sourceRoot = FileSystems.getDefault().getPath("Examples" + File.separator + "Dir2");
        Path targetRoot = FileSystems.getDefault().getPath("Examples" + File.separator + "Dir4/Dir2_copy"); //
        try {
            Files.walkFileTree(sourceRoot, new CopyFiles(sourceRoot, targetRoot));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void traverseEntireDir() {
        /* in order to do a directory traversal, u need to make use of Files.walkFileTree() which accepts
           a dir u want to traverse and the rules u want to be implemented defined by the implementation
           of a FileVisitor interface. it is an interface so u need an instance from a class so u either
           create a new class implementing this, or u define an anonymous class.

           the FileVisitor interface can have the ff be overridden for ur own purposes:
           1) preVisitDirectory() => accepts ref to a dir and its BasicFileAttributes instance
           2) postVisitDirectory() => accepts ref to a dir and Exception obj (when necessary, exception obj
                will be set when exception happens during traverse)
           3) visitFile() => accepts ref to a file and its BasicFileAttributes instance. this is where ur
                file processing logic resides. and ofc called only for files
           4) visitFileFailed() => exception thrown is passed here for u to decide what ur next action.
                can be called for files and dir
           but since there an already a SimpleFileVisitor that implements this interface, we'll make use
           of that and just enhance from it */

        // u cant assume the order
        Path dirPath = FileSystems.getDefault().getPath("Examples");
        try {
            Files.walkFileTree(dirPath, new PrintNames());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void systemSeparatorsFileStoresTempFileDir() {
        System.out.println("separator: " + File.separator);
        System.out.println("separator: " + FileSystems.getDefault().getSeparator());

        try {
            Path tempFile = Files.createTempFile("bbmTesting_", ".toDelete"); // if Path dir is not defined, temp file will be added to the systems default temp folder. for unix its in /tmp folder
            System.out.println("tempFile: " + tempFile.toAbsolutePath());
        } catch (IOException e) {
            e.printStackTrace();
        } // TODO: 11/9/18 delete ur created tmp files: cd /tmp;rm bbm*

        // Iterable<FileStore> drives = FileSystems.getDefault().getFileStores(); // TODO: 11/9/18 FileSystems.getDefault() returns ur machine's file system object and so it knows ur hard drives and can query them
        // for (FileStore drive : drives) {
        //     System.out.println("drive: " + drive);
        // }

        // Iterable<Path> rootDirs = FileSystems.getDefault().getRootDirectories();
        // for (Path root : rootDirs) {
        //     System.out.println("root: " + root);
        // }
    }

    private static void readingDirectoryDirectDescendants() {

        /* 1) DirectoryStream<T> => an interface used to "stream" the direct descendants of a directory
              and read / list it down => this interface implements Iterable so foreach will work for this
              like a collection list
           2) DirectoryStream.Filter<T> => a static interface within DirectoryStream (need to access Filter
              creating instance of DirectoryStream first, thus static) that is a lambda (long method
              is : creating an instance of a class that implements a one-method interface) whose only
              method is accept() and can define filter rule such as Files.isRegularFile()? meaning
              accept this in the list if a regularFile
           NOTE: Type T must be aligned for both interfaces

           3) instead of Filter, u can use String Glob like a Regex : *{txt,dat}*/

        DirectoryStream.Filter<Path> filterDirLongMethod = new DirectoryStream.Filter<Path>() {
            @Override
            public boolean accept(Path entry) throws IOException {
                // TODO: 11/9/18 important this: using reference with class/interface<T> generics will give u new class/interface<T> and so the method u implement will be expected to have params of generics <T> also. if u dont using <T>, then u have to play with Object and u need to do casting if u expect to play with Path. thats why if u use Path as <T> from the start, then u dont have to do casting anymore, SEE? remember this!!!!
                return Files.isDirectory(entry);
            }
        };

        DirectoryStream.Filter<Path> filterTxtLambda = entry -> Files.isRegularFile(entry);

        Path dirPath = FileSystems.getDefault().getPath("Examples");

        try {
            System.out.println("\nDirectories: ");
            DirectoryStream<Path> descendantList = Files.newDirectoryStream(dirPath, filterDirLongMethod);
            for (Path path : descendantList) {
                System.out.println(path.getFileName());
            }

            System.out.println("\nFiles: ");
            descendantList = Files.newDirectoryStream(dirPath, filterTxtLambda);
            for (Path path : descendantList) {
                System.out.println(path.getFileName());
            }

            System.out.println("\nUsing Glob as Filter: ");
            // Files.createTempFile(dirPath, "temp", ".dat");
            descendantList = Files.newDirectoryStream(dirPath, "*.{txt,dat}");
            for (Path path : descendantList) {
                System.out.println(path.getFileName());
            }
        } catch (IOException | DirectoryIteratorException e) {
            e.printStackTrace();
        }
    }

    private static void gettingFileMetadata() {
        Path filePath = FileSystems.getDefault().getPath("Examples","Dir1","file1.txt");

        try {
            System.out.println("path is: " + filePath.getFileName()
                    + "\n  exist? " + Files.exists(filePath)
                    + "\n  isDir? " + Files.isDirectory(filePath)
                    + "\n  isFile? " + Files.isRegularFile(filePath)
                    + "\n  isReadable? " + Files.isReadable(filePath)
                    + "\n  isWritable? " + Files.isWritable(filePath)
                    + "\n  isExecutable? " + Files.isExecutable(filePath)
                    + "\n  size? " + Files.size(filePath)
                    + "\n  lastModifiedDate? " + Files.getLastModifiedTime(filePath)
            ); // size wants try-catch

            // BasicFileAttributes provides basic file metadata info
            BasicFileAttributes fileAttr = Files.readAttributes(filePath, BasicFileAttributes.class); // want try-catch
            System.out.println("path is: " + filePath.getFileName()
                    + "\n  isDir? " + fileAttr.isDirectory()
                    + "\n  isFile? " + fileAttr.isRegularFile()
                    + "\n  isOther? " + fileAttr.isOther()
                    + "\n  size? " + fileAttr.size()
                    + "\n  creationTime? " + fileAttr.creationTime()
                    + "\n  lastAccessTime? " + fileAttr.lastAccessTime()
                    + "\n  lastModifiedTime? " + fileAttr.lastModifiedTime()
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void playingWithFiles() {

        /* COPYING FILE (WITH NAME ALREADY PROVIDED) */
        Path sourceFile = FileSystems.getDefault().getPath("Examples", "file1.txt");
        Path copyFile = FileSystems.getDefault().getPath("Examples", "file1_copy.txt");

        try {
            // Files.copy(sourceFile, copyFile); // 01 throws IOException when copyFile is already existing
            Files.copy(sourceFile, copyFile, StandardCopyOption.REPLACE_EXISTING); // 02 force replace if copyFile is already existing
            System.out.println("AfterCopy: \n  "
                    + sourceFile.normalize() + " exists? " + Files.exists(sourceFile) + "\n  "
                    + copyFile.normalize() + " exists? " + Files.exists(copyFile) + '\n');
        } catch (IOException e) {
            System.out.println("Error in copy: " + e.getMessage() + '\n'); // after catch error will move to succeeding code, unless they were affected by this prev error, it shud be fine
        }

        /* MOVING FILE */
        Path fileToMove = FileSystems.getDefault().getPath("Examples", "file1_copy.txt");
        Path fileDestination = FileSystems.getDefault().getPath("Examples", "Dir1", "file1_copy.txt");

        try {
            Files.move(fileToMove, fileDestination, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("AfterMove: \n  "
                    + fileToMove.normalize() + " exists? " + Files.exists(fileToMove) + "\n  "
                    + fileDestination.normalize() + " exists? " + Files.exists(fileDestination) + '\n');
        } catch (IOException e) {
            System.out.println("Error in move: " + e.getMessage() + '\n');
        }

        /* RENAMING A FILE: is just moving contents of origFileName into a newFileName in the same directory
           else if different directory, then its just plain old moving */
        Path origFileName = FileSystems.getDefault().getPath("Examples", "file1.txt");
        Path newFileName = FileSystems.getDefault().getPath("Examples", "file1_rename.txt");

        try {
            Files.move(origFileName, newFileName, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("AfterRename: \n  "
                    + origFileName.normalize() + " exists? " + Files.exists(origFileName) + "\n  "
                    + newFileName.normalize() + " exists? " + Files.exists(newFileName) + '\n');
        } catch (IOException e) {
            System.out.println("Error in rename: " + e.getMessage() + '\n');
        }
        // run this in the terminal afterwards: mv Examples/file1_rename.txt Examples/file1.txt

        /* DELETING A FILE: simpel delete or with existence checking */
        Path fileToDelete = FileSystems.getDefault().getPath("Examples", "Dir1", "file1_copy.txt");

        try {
            System.out.println("BeforeDelete: \n  "
                    + fileToDelete.normalize() + " exists? " + Files.exists(fileToDelete));
            Files.deleteIfExists(fileToDelete);
            System.out.println("BeforeDelete: \n  "
                    + fileToDelete.normalize() + " exists? " + Files.exists(fileToDelete) + '\n');
        } catch (IOException e) {
            System.out.println("Error in delete: " + e.getMessage() + '\n');
        }

    }

    private static void playingWithPaths() {
        // 01 RELATIVE PATH: from pwd + then define the name of ur file
        Path filePath = FileSystems.getDefault().getPath("WorkingDirectoryFile.txt");
        printFile(filePath);

        /* 02 RELATIVE PATH: from pwd + 1 directory (each dir shud be added as individual
           params OR combine all dir tree into 1 param) */
        // filePath = FileSystems.getDefault().getPath("files","Subdirectory.txt");
        filePath = FileSystems.getDefault().getPath("files/Subdirectory.txt");
        printFile(filePath);

        /* 03 ABSOLUTE PATH (exact location from root node) since cant use relative now.
           either u memorize the full path or u build it up. u can chop ur params and it
           wud still be the same */
        // filePath = Paths.get("/home/ymir/IdeaProjects/Java/01-java-core/OutThere.txt");
        filePath = Paths.get("/home/ymir/IdeaProjects/", "Java/01-java-core/", "OutThere.txt");
        printFile(filePath);

        /* 04 BUILDING ABSOLUTE PATH: this is not an existing file, only to get the full
           path of a file in the pwd (bec maybe u want to do something about it, like
           create it or read it, or move it, or delete, etc) */
        filePath = FileSystems.getDefault().getPath("WOrk.txt").toAbsolutePath();
        System.out.println(filePath);
        printFile(filePath);

        /* 05 BUILDING ABSOLUTE PATH USING CURRENT DIRECTORY: recoding the #02 */
        filePath = Paths.get(".", "files", "Subdirectory.txt");
        System.out.println("normalized: " + filePath.normalize());
        System.out.println("normalized + toAbsolute: " + filePath.normalize().toAbsolutePath());
        printFile(filePath);

        /* 06 PLAYING WITH . AND .. IN THE PATHS (think like a user inputs different params and
           they used . and .. so u need to normalize the path to evaluate the . and .. */
        filePath = FileSystems.getDefault().getPath(".", "files", "..", "files", "Subdirectory.txt");
        System.out.println(filePath.normalize().toAbsolutePath());
        printFile(filePath);

        /* 07 CHECKING EXISTENCE OF FILE/DIR */
        filePath = FileSystems.getDefault().getPath("WOrk.txt");
        System.out.println(filePath.getFileName()
                + "\n  exist? " + Files.exists(filePath)
                + "\n  isDir? " + Files.isDirectory(filePath)
                + "\n  isFile? " + Files.isRegularFile(filePath)); // even if not exist, no error
        filePath = FileSystems.getDefault().getPath("files"); // a directory
        System.out.println("path is: " + filePath.getFileName()
                + "\n  exist? " + Files.exists(filePath)
                + "\n  isDir? " + Files.isDirectory(filePath)
                + "\n  isFile? " + Files.isRegularFile(filePath)
                + "\n  isReadable? " + Files.isReadable(filePath)
                + "\n  isWritable? " + Files.isWritable(filePath)
                + "\n  isExecutable? " + Files.isExecutable(filePath));
    }

    private static void printFile(Path path) {
        try(BufferedReader reader = Files.newBufferedReader(path)) {
            System.out.println("Reading: " + path.getFileName());
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (NoSuchFileException e) { // more specific exception than IOException
            System.out.println(path.getFileName() + " file can't be opened.");
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println();
    }
}
