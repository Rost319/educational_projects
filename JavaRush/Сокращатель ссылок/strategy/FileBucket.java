package com.javarush.task.task33.task3310.strategy;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileBucket {

    Path path;

    public FileBucket() {
        try {
            path = Files.createTempFile(null, null);
            path.toFile().deleteOnExit();

            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {

        }
    }

    public long getFileSize() {
        try {
            return Files.size(path);
        } catch (IOException e) {

        }
        return 0;
    }

    public void putEntry(Entry entry) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))){
            objectOutputStream.writeObject(entry);
        }catch (IOException e) {

        }
    }

    public Entry getEntry() {
        if(getFileSize() > 0){
            try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))){
                return (Entry) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {

            }
        }
        return null;
    }

    public void remove() {
        try {
            Files.delete(path);
        } catch (IOException e) {

        }
    }
}
