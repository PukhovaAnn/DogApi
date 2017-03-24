package org.pukho.model;

/**
 * Created by Pukho on 03.02.2017.
 */
public class Dog {

    public Dog(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    private String name;

    public void setPictureLocation(String pictureLocation) {
        this.pictureLocation = pictureLocation;
    }

    private String pictureLocation;

    public void setId(long id) {
        this.id = id;
    }

    private long id;


    public String getPictureLocation() {
        return pictureLocation;
    }

    public Dog(String name, String pictureLocation) {
        this.name = name;
        this.pictureLocation = pictureLocation;
    }


    public Dog(Long id, String name, String pictureLocation) {
        this.id = id;
        this.name = name;
        this.pictureLocation = pictureLocation;
    }

    public Dog(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Dog() {}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Dog{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
