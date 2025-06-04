package org.practice.writers;

import org.practice.Configuration;

import java.io.IOException;
import java.io.RandomAccessFile;

public class TxtWriter{

    private static final String FILENAME = Configuration.RESOURCES_DIRECTORY + Configuration.OUTPUT_FILE;

    public void write(String data, boolean toFirstLine) {
        if (data == null) throw new NullPointerException("data is null");
        if (toFirstLine) writeInFirstLine(data);
        else writeToEnd(data);
    }

    private void writeInFirstLine(String data){
        try {
            RandomAccessFile raf = new RandomAccessFile(FILENAME, "rw");
            long startPosition = raf.getFilePointer();
            String firstLine = raf.readLine();
            long positionToModify;
            if (firstLine == null || firstLine.trim().isEmpty()) positionToModify = startPosition;
            else positionToModify = startPosition + firstLine.length();

            transferBytes(raf, positionToModify, data);
            raf.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void writeToEnd(String data){
        try {
            RandomAccessFile raf = new RandomAccessFile(FILENAME, "rw");
            raf.seek(raf.length());
            raf.writeBytes(data);
            raf.close();
        }catch (IOException e){throw new RuntimeException(e);}
    }

    public void deleteLastSymbolFromFirstLine() {
        try {
            RandomAccessFile raf = new RandomAccessFile(FILENAME, "rw");

            long startPosition = raf.getFilePointer();
            String firstLine = raf.readLine();

            if (firstLine == null || firstLine.trim().isEmpty()) throw new RuntimeException("There is nothing to delete");

            long positionToModify = startPosition + firstLine.length() - 1;

            transferBytes(raf, positionToModify);

            raf.setLength(raf.getFilePointer());
            raf.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private void transferBytes(RandomAccessFile raf, long positionToModify, String... strings) throws IOException {
        raf.seek(positionToModify);

        long fileLength = raf.length();
        byte[] buffer = new byte[(int) (fileLength - raf.getFilePointer())];
        raf.read(buffer);

        raf.seek(positionToModify);

        if (strings != null && strings.length > 0) {
            raf.writeBytes(strings[0]);
            if (buffer.length > 0){
                raf.write(buffer);
            }
            raf.seek(raf.length());
        }else {
            raf.write(buffer, 1, buffer.length - 1);
        }
    }
}
