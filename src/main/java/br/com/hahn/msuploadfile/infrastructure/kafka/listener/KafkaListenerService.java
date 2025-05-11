package br.com.hahn.msuploadfile.infrastructure.kafka.listener;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListenerService {

    @KafkaListener(topics = "file-upload", groupId = "ms-upload-file")
    public void listen(String message) {
        System.out.println("Message received: " + message);
    }
}
