package br.com.hahn.msuploadfile.presentation.api.controller;

import br.com.hahn.msuploadfile.application.exception.FileEmptyException;
import br.com.hahn.msuploadfile.application.exception.WrongFileNameException;
import br.com.hahn.msuploadfile.application.service.NamesListService;
import br.com.hahn.msuploadfile.application.dto.NamesUploadResponseDTO;
import br.com.hahn.msuploadfile.infrastructure.kafka.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/names")
public class NamesListController {

    private final NamesListService namesListService;
    private final KafkaProducerService kafkaProducerService;

    public NamesListController(NamesListService namesListService, KafkaProducerService kafkaProducerService) {
        this.namesListService = namesListService;
        this.kafkaProducerService = kafkaProducerService;
    }

    @PostMapping("/upload")
    public ResponseEntity<NamesUploadResponseDTO> uploadNames(@RequestParam("file")MultipartFile file){
        validadeEmptyFile(file);
        validadeFileName(file);
        NamesUploadResponseDTO response = namesListService.saveNamesList(file);
        kafkaProducerService.sendMessage("File uploaded successfully with ID: " + response.listId());
        return ResponseEntity.ok(response);
    }

    void validadeEmptyFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new FileEmptyException("File is empty");
        }
    }

    public void validadeFileName(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        if (fileName == null || (!fileName.endsWith(".txt") && !fileName.endsWith(".csv"))) {
            throw new WrongFileNameException("The file must be .txt or .csv");
        }
    }
}
