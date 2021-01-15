package ru.sapteh;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.zip.Deflater;
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
            zipOutputStream.setLevel(-1);
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
//            Path path = Paths.get("C:/Test/directory/image.jpg");
//            ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream("C:/Test/directory/archive.zip"));
//            ZipEntry zipEntry = new ZipEntry("image.jpg");
//            zipOutputStream.putNextEntry(zipEntry);
//            Files.copy(path, zipOutputStream);
//            zipOutputStream.close();
//            System.out.println(zipEntry.getCompressedSize()/1024);


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
//
//    Path sourcePath = Paths.get("C:/Test/directory");
//            Files.copy(inputStream, sourcePath.resolve("image.jpg"), StandardCopyOption.REPLACE_EXISTING);
//
//                    System.out.println("Введите путь где создать архив с именем и расширением: ");
//                    String archivePath = bf.readLine();
//                    ZipOutputStream zipOutputStream = new ZipOutputStream(new FileOutputStream(archivePath));
//
//                    System.out.println("Введите как назвать картинку в ахиве вместе с расширением: ");
//                    String imageArchiveName = bf.readLine();
//                    zipOutputStream.putNextEntry(new ZipEntry(imageArchiveName));
//
//                    Files.copy(sourcePath.resolve("image.jpg"), zipOutputStream);
//                    Files.delete(sourcePath.resolve("image.jpg"));
//
//                    inputStream.close();
//                    zipOutputStream.close();
