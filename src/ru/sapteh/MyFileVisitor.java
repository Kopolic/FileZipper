package ru.sapteh;

import java.io.File;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;

public class MyFileVisitor extends SimpleFileVisitor<Path> {

    private List<File> list = new ArrayList<>();
    private List<Long> listSize = new ArrayList<>();
    private int id = 1;
    private boolean flag = true;

    public List<File> getList() {
        return list;
    }
    public long getSize(){
        long size = listSize.get(id);
        ++id;
        return size;
    }
    
    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes atr){
        list.add(file.toFile());
        listSize.add(atr.size());
        return FileVisitResult.CONTINUE;
    }

}
