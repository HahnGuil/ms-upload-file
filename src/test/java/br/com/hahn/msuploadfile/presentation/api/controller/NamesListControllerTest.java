package br.com.hahn.msuploadfile.presentation.api.controller;

import br.com.hahn.msuploadfile.application.dto.NamesUploadResponseDTO;
import br.com.hahn.msuploadfile.application.exception.FileEmptyException;
import br.com.hahn.msuploadfile.application.exception.WrongFileNameException;
import br.com.hahn.msuploadfile.application.service.NamesListService;
import br.com.hahn.msuploadfile.infrastructure.kafka.KafkaProducerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class NamesListControllerTest {

    private NamesListService namesListService;
    private KafkaProducerService kafkaProducerService;
    private NamesListController namesListController;

    @BeforeEach
    void setUp() {
        namesListService = mock(NamesListService.class);
        kafkaProducerService = mock(KafkaProducerService.class);
        namesListController = new NamesListController(namesListService, kafkaProducerService);
    }

    @Test
    void uploadNames_ShouldReturnResponse_WhenFileIsValid() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "names.csv", "text/csv", "name1,name2".getBytes());
        NamesUploadResponseDTO responseDTO = new NamesUploadResponseDTO(true, "SUCCESS", 1L);
        when(namesListService.saveNamesList(file)).thenReturn(responseDTO);

        // Act
        ResponseEntity<NamesUploadResponseDTO> response = namesListController.uploadNames(file);

        // Assert
        assertNotNull(response);
        assertEquals(200, response.getStatusCode().value());
        assertNotNull(response.getBody());
        assertEquals(1L, response.getBody().listId());
        verify(namesListService, times(1)).saveNamesList(file);
        verify(kafkaProducerService, times(1)).sendMessage("File uploaded successfully with ID: 1");
    }

    @Test
    void uploadNames_ShouldThrowFileEmptyException_WhenFileIsEmpty() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "names.csv", "text/csv", new byte[0]);

        // Act & Assert
        FileEmptyException exception = assertThrows(FileEmptyException.class, () -> namesListController.uploadNames(file));
        assertEquals("File is empty", exception.getMessage());
        verify(namesListService, never()).saveNamesList(file);
        verify(kafkaProducerService, never()).sendMessage(anyString());
    }

    @Test
    void uploadNames_ShouldThrowWrongFileNameException_WhenFileNameIsInvalid() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "names.pdf", "application/pdf", "content".getBytes());

        // Act & Assert
        WrongFileNameException exception = assertThrows(WrongFileNameException.class, () -> namesListController.uploadNames(file));
        assertEquals("The file must be .txt or .csv", exception.getMessage());
        verify(namesListService, never()).saveNamesList(file);
        verify(kafkaProducerService, never()).sendMessage(anyString());
    }

    @Test
    void validadeEmptyFile_ShouldThrowException_WhenFileIsEmpty() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "names.csv", "text/csv", new byte[0]);

        // Act & Assert
        FileEmptyException exception = assertThrows(FileEmptyException.class, () -> namesListController.validadeEmptyFile(file));
        assertEquals("File is empty", exception.getMessage());
    }

    @Test
    void validadeFileName_ShouldThrowException_WhenFileNameIsInvalid() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "names.docx", "application/vnd.openxmlformats-officedocument.wordprocessingml.document", "content".getBytes());

        // Act & Assert
        WrongFileNameException exception = assertThrows(WrongFileNameException.class, () -> namesListController.validadeFileName(file));
        assertEquals("The file must be .txt or .csv", exception.getMessage());
    }

    @Test
    void validadeFileName_ShouldNotThrowException_WhenFileNameIsValid() {
        // Arrange
        MockMultipartFile file = new MockMultipartFile("file", "names.csv", "text/csv", "content".getBytes());

        // Act & Assert
        assertDoesNotThrow(() -> namesListController.validadeFileName(file));
    }
}