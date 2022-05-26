package com.will;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author haonan.wen
 * @createTime 2022/5/19 下午6:40
 */
public class TestFilesWalkFileTree {

    public static void main(String[] args) throws IOException {
        Files.delete(Paths.get("/Users/wenhaonan/IdeaProjects/rpc-framework/nio-demo"));
    }

    public static void m1(String[] args) throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("/Users/will/IdeaProjects/rpc-framework/nio-demo"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("===>" + dir);
                dirCount.incrementAndGet();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("===>" + file);
                fileCount.incrementAndGet();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(dirCount.get());
        System.out.println(fileCount.get());
    }
}
