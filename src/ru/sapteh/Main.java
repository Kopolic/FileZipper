package ru.sapteh;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class Main {
    public static void main(String[] args) {

        try(BufferedReader bf = new BufferedReader(new InputStreamReader(System.in))){

            System.out.println("Введите путь к папке для её архивации: ");
            String pathZip = bf.readLine();
            MyFileVisitor myFileVisitor = new MyFileVisitor();

            Path path = Paths.get(pathZip);

            Files.walkFileTree(path, myFileVisitor);
            FileOutputStream fileOutputStream = new FileOutputStream(pathZip + ".zip");
            ZipOutputStream zipOutputStream = new ZipOutputStream(fileOutputStream);
            zipOutputStream.setLevel(9);
            boolean bool = false;

            for (File file: myFileVisitor.getList()) {
                ZipEntry zipEntry = new ZipEntry(file.getName());
                zipOutputStream.putNextEntry(zipEntry);
                if (bool) {
                    Files.copy(file.toPath(), zipOutputStream);
                    zipOutputStream.closeEntry();
                    long myFileVisitorSize = myFileVisitor.getSize();
                    double doubleSize =  100 - ((double) zipEntry.getCompressedSize()/myFileVisitorSize)*100;
                    System.out.printf("%s | %d (%d) %.0f%% \n", file.getAbsolutePath(), myFileVisitorSize, zipEntry.getCompressedSize(), doubleSize);
                }
                bool = true;
            }
            zipOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}