package com.hoken;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class PrintNames extends SimpleFileVisitor<Path> {
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        /* Process:
           1) do ur thing with the file
           2) return traversal next action which is defined using FileVisitResult
           => an enum with ff values: CONTINUE, SKIP_SIBLINGS, SKIP_SUBTREE and TERMINATE */
        System.out.println("file: " + file.normalize());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        System.out.println("dir: " + dir.normalize());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        System.out.println("Error accessing file: " + file.normalize() + ", " + exc.getMessage());
        return FileVisitResult.CONTINUE;
    }
}
