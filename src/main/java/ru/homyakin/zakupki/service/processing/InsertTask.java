package ru.homyakin.zakupki.service.processing;

import ru.homyakin.zakupki.database.BaseRepository;

public class InsertTask<T> implements Runnable {
    private BaseRepository<T> repository;
    private T obj;

    public InsertTask(BaseRepository<T> repository, T obj) {
        this.repository = repository;
        this.obj = obj;
    }

    @Override
    public void run() {
        repository.insert(obj);
    }
}
