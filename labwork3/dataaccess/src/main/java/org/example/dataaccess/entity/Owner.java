package org.example.dataaccess.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@RequiredArgsConstructor
@Entity
@Table(name="Owners")
public class Owner {
    @Id
    @GeneratedValue
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name =  "birth_date")
    private LocalDate birthDate;

    @OneToMany(mappedBy = "owner", fetch = FetchType.LAZY)
    @Fetch(FetchMode.SUBSELECT)
    @Transient
    private List<Cat> cats = new ArrayList<>();

    public Owner(String name, LocalDate birthDate) {
        this.name = name;
        this.birthDate = birthDate;
    }

    public void addCat(Cat cat) {
        this.cats.add(cat);
    }

}

