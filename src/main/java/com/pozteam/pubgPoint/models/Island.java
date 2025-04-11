package com.pozteam.pubgPoint.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "islands")
public class Island {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name", nullable = false,length = 30)
    private String name;

    @OneToMany(mappedBy = "island", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Point> points;
    public Island() {

    }

    public Island(String name) {
        this.name = name;
    }

    public Integer getId() {
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

    public List<Point> getPoints() {
        return points;
    }

    public void setPoints(List<Point> points) {
        this.points = points;
    }
}
