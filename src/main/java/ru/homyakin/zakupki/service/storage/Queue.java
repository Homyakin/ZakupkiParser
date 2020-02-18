package ru.homyakin.zakupki.service.storage;

public abstract class Queue<T> {
    public abstract void put(T obj);

    public abstract T take();

    public abstract boolean isEmpty();
}
