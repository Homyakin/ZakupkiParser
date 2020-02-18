package ru.homyakin.zakupki.service.storage;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.homyakin.zakupki.models.ParseFile;

@Service
public class ParseFileQueue extends Queue<ParseFile> {
    private BlockingQueue<ParseFile> queue = new LinkedBlockingQueue<ParseFile>();
    private static final Logger logger = LoggerFactory.getLogger(ParseFileQueue.class);

    @Override
    public void put(ParseFile obj) {
        try {
            queue.put(obj);
        } catch (InterruptedException e) {
            logger.error("Unable to put data, thread was interrupted", e);
            throw new IllegalThreadStateException("Unable to put data, thread was interrupted");
        }
    }

    @Override
    public ParseFile take() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            logger.error("Unable to take data, thread was interrupted", e);
            throw new IllegalThreadStateException("Unable to take data, thread was interrupted");
        }
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
