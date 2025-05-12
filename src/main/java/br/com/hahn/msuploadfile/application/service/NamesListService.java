package br.com.hahn.msuploadfile.application.service;

import br.com.hahn.msuploadfile.application.dto.NamesUploadResponseDTO;
import br.com.hahn.msuploadfile.infrastructure.file.FileManipulator;
import br.com.hahn.msuploadfile.domain.model.NamesList;
import br.com.hahn.msuploadfile.domain.repository.NamesListRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class NamesListService {

    private final NamesListRepository namesListRepository;
    private final FileManipulator fileManipulator;

    public NamesListService(NamesListRepository namesListRepository, FileManipulator fileManipulator) {
        this.namesListRepository = namesListRepository;
        this.fileManipulator = fileManipulator;
    }

    public NamesUploadResponseDTO saveNamesList(MultipartFile file) {
        try {
            List<String> names = fileManipulator.convertToNamesList(file);

            NamesList namesList = new NamesList();
            namesList.addNames(names);

            NamesList savedList = namesListRepository.save(namesList);

            return new NamesUploadResponseDTO(true, "Nomes importados com sucesso", savedList.getId());
        } catch (IOException e) {
            return new NamesUploadResponseDTO(false, "Erro ao processar arquivo: " + e.getMessage(), null);
        }
    }



}
