package org.pukho.controller;

import org.pukho.PictureDownloadService;
import org.pukho.model.Dog;
import org.pukho.service.DogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.List;
import java.util.Optional;

/**
 * Created by Pukho on 03.02.2017.
 */

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class DogController {

    @Autowired
    private PictureDownloadService saver;

    @Autowired
    private DogService service;

    @RequestMapping(value = "/dog", method = RequestMethod.GET)
    public ResponseEntity<List<Dog>> listAllDogs() {
        List<Dog> dogs = service.getAll();

        if(dogs.isEmpty()){
            return new ResponseEntity<List<Dog>>(HttpStatus.NO_CONTENT);//You many decide to return HttpStatus.NOT_FOUND
        }
        return new ResponseEntity<List<Dog>>(dogs, HttpStatus.OK);
    }

    @RequestMapping(value = "/dogMultipart", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<Dog> updateUser(
            @RequestPart("dog") String dog,
            @RequestPart("file") MultipartFile file) {

        System.out.println(dog);
        System.out.println(file.getName());
        return new ResponseEntity<Dog>(new Dog("df", ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/dogEntity", method = RequestMethod.POST)
    public ResponseEntity<Dog> updateDog(
            @RequestBody Dog dog) {

        System.out.println(dog);
        return new ResponseEntity<Dog>(new Dog("df", ""), HttpStatus.OK);
    }


    @RequestMapping(value = "/dog", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<Dog> updateUser(
                                          @RequestPart("dog") Dog dog,
                                          @RequestPart("file") MultipartFile file) {

        System.out.println(dog);
        System.out.println(file.getName());
        return new ResponseEntity<Dog>(new Dog("df", ""), HttpStatus.OK);
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.GET)
    public ResponseEntity<Dog> getTrack(@PathVariable("id") Long id) {

        Dog dog = service.get(id);
        if (dog == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(dog, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/dog/{id}", method = RequestMethod.POST, consumes = {"multipart/form-data"})
    public ResponseEntity<Dog> createUser(@PathVariable("id") Long id,
                                           @RequestPart("dog") Dog dog,
                                           @RequestPart("file") MultipartFile file) {

        Optional<Path> picturePath = saver.savePicture(file);
        if (picturePath.isPresent()) {
            dog.setPictureLocation(picturePath.get().toString());
        }
        service.save(dog);
        return new ResponseEntity<Dog>(new Dog(), HttpStatus.OK);
    }



    @RequestMapping(value = "/dog/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteUser(@PathVariable("id") Integer id) {
        service.remove(id);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
