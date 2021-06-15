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
    private final Map<String, ParseFileQueue> storage = new ConcurrentHashMap<>();

    public void insert(String region, ParseFile file) {
        var key = region + file.getFolder().getName();
        if (!storage.containsKey(region + file.getFolder().getName())) {
            storage.put(key, new ParseFileQueue());
        }
        storage.get(key).put(file);
    }

    public Map<String, ParseFileQueue> getMap() {
        return storage;
    }

    @Scheduled(fixedDelay = 10 * 60 * 1000)
    public void clear() {
        var keysToDelete = new ArrayList<String>();
        for (var entry: storage.entrySet()) {
            if (entry.getValue().isEmpty()) {
                keysToDelete.add(entry.getKey());
            }
        }
        for (var key: keysToDelete) {
            storage.remove(key);
        }
    }
}
