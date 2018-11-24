package com.hoken;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/* task is to copy entire Dir2 into non-existing Dir4 (so Dir4 must be created 1st? or create later??)
   1. sourcePath : "Examples/Dir2/Dir3/file1.txt"
   2. sourceRoot : "Examples/Dir2"
   3. targetRoot : "Examples/Dir4/Dir2_copy" where Dir4 must already be existing bec Dir2_copy u will create inside Dir4 so Dir4 must already be existing, or else error right away
   4. relativizedPath = sourceRoot.relativize(sourcePath)
        => isolate starting tree with the filePath being processed (subtract) => order sourceRoot-sourcePath
        => (which is computed by full sourcePath - sourceRoot)
        => "Dir3/file1.txt"
   5. resolvedPathForCopy = targetRoot.resolve(relativizedPath)
        => combine the isolated filePath with its new parentDir (addition) => order targetRoot + isolatedPath
        => (which is computed by targetRoot + relativizedPath)
        => "Examples/Dir4/Dir2_copy" + "Dir3/file1.txt" */
public class CopyFiles extends SimpleFileVisitor<Path> {
    private Path sourceRoot;
    private Path targetRoot;

    public CopyFiles(Path sourceRoot, Path targetRoot) {
        this.sourceRoot = sourceRoot;
        this.targetRoot = targetRoot;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        // if source dir is not existing in target dir, create it first => Paths have same computation as with files but this time instead of using file, u use dir params
        Path mirrorDir = targetRoot.resolve(sourceRoot.relativize(dir));
        System.out.println("mirrorDir: " + mirrorDir.toAbsolutePath() + '\n');

        if (Files.notExists(mirrorDir)) {
            try {
                Files.copy(dir, mirrorDir);
                System.out.println("Directory created.");
            } catch (IOException e) {
                System.out.println(e.getMessage());
                return FileVisitResult.SKIP_SUBTREE; // not able to create the directory so u need to skip it altogether or else for every file u process, error will just continue to happen since u cant copy a file to a directory that is not existing in the 1st place
            }
        } else {
            System.out.println("Directory already existing. But continue copying files into it since any existing files will just be overridden.\n");
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        /* make a copy to target, i need a sourcePath, a targetPath
           sourcePath is the file itself??
           targetPath must be targetRoot.resolve(isolatedFilePath)
             where isolatedFilePath = sourceRoot.relativize(sourcePath)
           */
        Path mirrorFile = targetRoot.resolve(sourceRoot.relativize(file));
        try {
            Files.copy(file, mirrorFile, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error copying file " + file.normalize() + " " + exc.getMessage());
        return FileVisitResult.CONTINUE;
    }
}
