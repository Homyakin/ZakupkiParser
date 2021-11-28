package ru.homyakin.zakupki.service.storage;

import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.models.ParseFile;

//Хранит в себе мапу <Регион+папка, файлы> для распараллеленной вставки
@Service
public class RegionFilesStorage {
    private final ParseFileQueue storage = new ParseFileQueue();

    public void insert(ParseFile file) {
        storage.put(file);
    }

    public ParseFileQueue getQueue() {
        return storage;
    }
}
