package br.com.hahn.msuploadfile.presentation.api.controller;

import br.com.hahn.msuploadfile.infrastructure.kafka.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test-kafka")
public class TestKafkaResponseController {

    private final KafkaProducerService kafkaProducerService;

    public TestKafkaResponseController(KafkaProducerService kafkaProducerService) {
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping(value = "/send")
    public ResponseEntity<String> sendMessage(@RequestBody String message) {
        kafkaProducerService.sendMessage(message);
        return ResponseEntity.ok("Message sent successfully");
    }


}
