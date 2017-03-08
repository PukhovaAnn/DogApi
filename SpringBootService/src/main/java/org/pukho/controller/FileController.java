package org.pukho.controller;

import org.pukho.PictureDownloadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

/**
 * Created by Pukho on 05.03.2017.
 */

@RestController
@CrossOrigin(origins = "http://localhost:9000")
public class FileController {

    @Autowired
    private PictureDownloadService pictureService;

    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void getFileHandler(HttpServletResponse response, @RequestParam("pictureDog") String picture) {

        final Optional<Path> filePath = pictureService.getPicturePathByLocation(picture);

        if (filePath.isPresent()) {
            try {
                Files.copy(filePath.get(), response.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("QO");
        }
    }
}
