package ru.homyakin.zakupki.service.processing;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;

@Component
public class DatabasePoolTaskExecutor extends ThreadPoolTaskExecutor {
    public DatabasePoolTaskExecutor() {
        super();
        setCorePoolSize(50);
        setMaxPoolSize(100);
        setThreadNamePrefix("DatabasePoolTask");
    }
}
