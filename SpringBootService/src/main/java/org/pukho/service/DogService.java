package org.pukho.service;


import org.pukho.model.Dog;

import java.util.List;

/**
 * Created by Pukho on 05.03.2017.
 */
public interface DogService {
     void save(Dog dog);
     public Dog get(long id);
     public void remove(Integer id);
     public void update(Dog dog);
     List<Dog> getAll();
}
