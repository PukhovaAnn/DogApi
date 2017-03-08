package org.pukho.service;


import org.pukho.model.Dog;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Pukho on 05.03.2017.
 */
public class DogMapper implements RowMapper<Dog> {

    @Override
    public Dog mapRow(ResultSet rs, int rowNum) throws SQLException {
        Dog dog = new Dog();
        dog.setId(rs.getLong("id"));
        dog.setName(rs.getString("name"));
        dog.setPictureLocation(rs.getString("picture"));
        return dog;
    }
}
