package org.practice.repository;

import org.practice.record.Identifiable;

import java.util.List;

public interface IDataRepository<T extends Identifiable> {
    List<T> getAll();
    T getById(int id);
    void add(T t);
    T update(T t);
    void delete(T t);
}
