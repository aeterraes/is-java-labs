package org.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Cats")
public class Cat {

    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "birth_date")
    private LocalDate birthDate;
    @Column(name = "breed")
    private String breed;
    @Column(name = "color")
    private String color;

    @Column(name = "owner_id")
    private int ownerId;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "CatFriends",
            joinColumns = @JoinColumn(name = "cat1_id"),
            inverseJoinColumns = @JoinColumn(name = "cat2_id")
    )
    private List<Cat> friends;

    @ManyToOne
    @JoinColumn(name = "owner_id", insertable = false, updatable = false)
    private Owner owner;

    public Cat(){
    }

    public Cat(String name, LocalDate birthDate, String breed, String color, int ownerId) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.friends = new ArrayList<>();
        this.ownerId = ownerId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Owner getOwner() {
        return owner;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public int getOwnerId() { return ownerId;}

    public void setOwnerId(int ownerId) {this.ownerId = ownerId;}

    public List<Cat> getFriends() {
        return friends;
    }

    public void setFriends(List<Cat> friends) {
        this.friends = friends;
    }

    public void addFriend(Cat friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    public void removeFriend(Cat friend) {
        friends.remove(friend);
        friend.getFriends().remove(this);
    }
}
