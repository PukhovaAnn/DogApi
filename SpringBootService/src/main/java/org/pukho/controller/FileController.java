package org.pukho.controller;

import org.pukho.PictureDownloadService;
import org.pukho.model.Dog;
import org.pukho.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class FileController {

    @Autowired
    private PictureDownloadService pictureService;

    @Autowired
    private DogService service;

    @RequestMapping(value = "/picture/{id}", method = RequestMethod.GET)
    public void getFileHandler(HttpServletResponse response,
                               @PathVariable("id") Long id) {

        Dog dog = service.get(id);
        Optional<Path> filePath = pictureService
                .getImagePathByLocation(dog.getPictureLocation());

        if (filePath.isPresent()) {
            try {
                System.out.println(filePath.get().toString());
                Files.copy(filePath.get(), response.getOutputStream());
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        } else {
             response.reset();
        }
    }
}
