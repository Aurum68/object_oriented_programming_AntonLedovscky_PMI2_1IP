package org.practice.repository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.practice.record.Identifiable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataRepository<T extends Identifiable> implements IDataRepository<T>{

    private final String fileName;
    private final Class<T> clazz;
    private final ObjectMapper mapper = new ObjectMapper();
    private List<T> data = new ArrayList<>();

    public DataRepository(String fileName, Class<T> clazz) {
        this.fileName = fileName;
        this.clazz = clazz;
        loadData();
    }

    private void loadData() {
        try {
            File file = new File(fileName);
            if (!file.exists()){
                data = new ArrayList<>();
                return;
            }
            data = mapper.readValue(file, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        }catch (IOException e){
            System.err.println(e.getMessage());
            data = new ArrayList<>();
        }
    }

    private void save() {
        try {
            mapper.writeValue(new File(fileName), data);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(data);
    }

    @Override
    public T getById(int id) {
        for (T t : data) {
            if (t.getId() == id) return t;
        }
        return null;
    }

    @Override
    public void add(T t) {
        data.add(t);
        save();
    }

    @Override
    public T update(T t) {
        data.set(data.indexOf(t), t);
        save();
        return t;
    }

    @Override
    public void delete(T t) {
        data.remove(t);
        save();
    }
}
