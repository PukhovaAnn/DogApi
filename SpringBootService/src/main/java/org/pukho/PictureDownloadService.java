package org.pukho;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

import static java.nio.file.Files.exists;
import static java.util.Objects.requireNonNull;


/**
 * Created by Pukho on 02.03.2017.
 */
@Component
public class PictureDownloadService {

    @Value("${files.directory}")
    private String directory;

    private String picturesDirectory;

    @PostConstruct
    private void prepareWorkingDirectory() {
        final String workingDirectory = System.getProperty("user.home") + File.separator + directory;
        picturesDirectory = workingDirectory + File.separator + "avatars";
        new File(picturesDirectory).mkdirs();
    }

        public Optional<Path> savePicture(final MultipartFile file) {
            requireNonNull(file, "picture must not be null");
            try (InputStream in = file.getInputStream()) {
                return Optional.of(savePicture(in, file.getOriginalFilename()));
            } catch (IOException e) {
                System.out.println("savw");
            }
            return Optional.empty();
        }


    private Path savePicture(final InputStream stream, final String fileName) throws IOException {
        final Path path = Paths.get(picturesDirectory, fileName);
        if (Files.exists(path)) {
            return path;
        }
        Files.copy(stream, path);
        return path;
    }


    public Optional<Path> getPicturePathByLocation(String fileName) {
        System.out.println(picturesDirectory);
        Path path = getPathByName(fileName, picturesDirectory).orElse(null);
        return getPathByName(fileName, picturesDirectory);
    }

    private Optional<Path> getPathByName(String filename, final String workingDirectory) {
        requireNonNull(filename, "filename must not be null");

        filename = "CAM03215.jpg";
        final Path path = Paths.get(workingDirectory, filename);
        if (!exists(path)) {
            return Optional.empty();
        }
        return Optional.of(path);
    }

}
