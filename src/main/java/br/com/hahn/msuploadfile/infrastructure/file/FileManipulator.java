package br.com.hahn.msuploadfile.infrastructure.file;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FileManipulator {

    public List<String> convertToNamesList(MultipartFile file) throws IOException {
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        String filename = Optional.ofNullable(file.getOriginalFilename()).orElse("");

        if (content.isEmpty()) return Collections.emptyList();

        String regex = filename.endsWith(".csv") ? "," : "[;\\r?\\n]";

        return Arrays.stream(content.split(regex))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .toList();
    }
}

