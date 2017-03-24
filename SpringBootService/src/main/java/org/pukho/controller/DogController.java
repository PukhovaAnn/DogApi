package org.pukho.controller;

import org.pukho.PictureDownloadService;
import org.pukho.model.Dog;
import org.pukho.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by Pukho on 03.02.2017.
 */

@RestController
public class DogController {

    @Autowired
    private PictureDownloadService saver;

    @Autowired
    private DogService service;

    @Value("${files.default}")
    private String defaultPictureUrl;

    @Value("${files.default}")
    private String defaultPictureName;

    @RequestMapping(value = "/dog", method = RequestMethod.GET)
    public ResponseEntity<List<Dog>> getAllDogs() {
        List<Dog> dogs = service.getAll();

        if(dogs.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(dogs, HttpStatus.OK);
    }


    @RequestMapping(value = "/dog", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<Dog> createDog(
                                          @RequestPart(value = "dog") Dog dog,
                                          @RequestPart(value = "file", required = false) MultipartFile file) throws MalformedURLException {
        Optional<Path> filePath;
        if (file==null) {
               filePath = saver.getPathByName(defaultPictureName);
        }
        else {
            filePath = saver.savePictureFromFile(file);
        }

        if (filePath.isPresent()) {
            dog.setPictureLocation(filePath.get().toString());
            Dog savedDog = service.save(dog);
            return new ResponseEntity<>(savedDog, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        }
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.GET)
    public ResponseEntity<Dog> getDog(@PathVariable("id") Long id) {
        System.out.println("dog here! ");

        Dog dog = service.get(id);
        return new ResponseEntity<Dog>(dog, HttpStatus.OK);
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<Dog> updateDog(@PathVariable("id") Long id,
                                           @RequestPart("dog") Dog dog,
                                           @RequestPart(value = "file", required = false) MultipartFile file) {

        if (file!=null) {
            Optional<Path> filePath = saver.savePictureFromFile(file);
            if (filePath.isPresent()) {
                dog.setPictureLocation(filePath.get().toString());
            }
        }
        Dog updatedDog = service.update(dog);
        return new ResponseEntity<>(updatedDog, HttpStatus.OK);
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteDog(@PathVariable("id") Integer id) {
        service.remove(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
