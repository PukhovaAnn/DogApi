package org.pukho.service;

import org.pukho.Dao;
import org.pukho.model.Dog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Pukho on 05.03.2017.
 */
@Repository
public class DogServiceImpl implements DogService {

    @Autowired
    private Dao dao;

    @Override
    public void save(Dog dog) {
        String sql = "insert into dogs (name, picture) values (:name, :pictureLocation)";
        dao.save(sql, dog);
    }

    @Override
    public Dog get(long id) {
        String sql = "select * from dogs where id=:id";
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        return dao.get(sql, params, new DogMapper());
    };

    @Override
    public void remove(Integer id) {
        String sql = "delete from dogs where id = :id";

        Map<String, Object> params = new HashMap<>();
        params.put("id", id);

        dao.remove(sql, params);
    }

    @Override
    public void update(Dog dog) {
        String sql = "UPDATE dogs set name=:name, picture=:pictureLocation where id=:id";
        dao.update(sql, dog);
    }

    @Override
    public List<Dog> getAll() {
        String sql = "select * from dogs";
        return dao.getAll(sql, new DogMapper());
    }
}
