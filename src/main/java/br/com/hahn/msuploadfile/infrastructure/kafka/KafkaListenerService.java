package br.com.hahn.msuploadfile.infrastructure.kafka;

import org.slf4j.Logger;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(KafkaListenerService.class);

    @KafkaListener(topics = "file-upload", groupId = "ms-upload-file")
    public void listen(String message) {
        logger.info("Message received: {}", message);
    }
}
