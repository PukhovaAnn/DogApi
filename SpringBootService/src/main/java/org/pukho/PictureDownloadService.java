package org.pukho;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;

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

    @Value("${files.default}")
    private String defaultPicture;

    @PostConstruct
    private void prepareWorkingDirectory() {
        final String workingDirectory = System.getProperty("user.home") + File.separator + directory;
        picturesDirectory = workingDirectory + File.separator + "avatars";
        new File(picturesDirectory).mkdirs();

        saveDefaultPicture();
    }

    private void saveDefaultPicture() {
        ClassLoader classLoader = this.getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(defaultPicture);
        try {
            savePicture(inputStream, defaultPicture);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

        public Optional<Path> savePictureFromFile(final MultipartFile file) {
                try (InputStream in = file.getInputStream()) {
                    return Optional.of(savePicture(in, file.getOriginalFilename()));
                } catch (IOException e) {
                    System.out.println(e.toString());
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

    public Optional<Path> getImagePathByLocation(String filePath) {
        Path path = Paths.get(filePath);
        if (!exists(path)) {
            return Optional.empty();
        }
        return Optional.of(path);
    }

    public Optional<Path> getPathByName(String fileName) {
        final Path path = Paths.get(picturesDirectory, fileName);

        if (!exists(path)) {
            return Optional.empty();
        }
        return Optional.of(path);
    }
}
