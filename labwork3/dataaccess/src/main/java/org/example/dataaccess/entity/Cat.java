package org.example.dataaccess.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Setter
@Getter
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

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "CatFriends",
            joinColumns = @JoinColumn(name = "cat1_id"),
            inverseJoinColumns = @JoinColumn(name = "cat2_id")
    )

    @Fetch(FetchMode.SUBSELECT)
    private List<Cat> friends;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private Owner owner;


    public Cat(String name, LocalDate birthDate, String breed, String color) {
        this.name = name;
        this.birthDate = birthDate;
        this.breed = breed;
        this.color = color;
        this.friends = new ArrayList<>();
    }

    public void addFriend(Cat friend) {
        friends.add(friend);
        friend.getFriends().add(this);
    }

    public void removeFriend(Cat friend) {
        friends.remove(friend);
        friend.getFriends().remove(this);
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Cat cat = (Cat) o;
        return false;
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}