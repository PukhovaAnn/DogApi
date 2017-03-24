package org.pukho.service;


import org.pukho.model.Dog;

import java.util.List;
import java.util.Optional;

/**
 * Created by Pukho on 05.03.2017.
 */
public interface DogService {
     Dog save(Dog dog);
     Dog get(long id);
     void remove(Integer id);
     Dog update(Dog dog);
     List<Dog> getAll();
}
