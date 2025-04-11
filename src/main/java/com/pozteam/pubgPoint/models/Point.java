package com.pozteam.pubgPoint.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "points")
public class Point {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "island_id",referencedColumnName = "id")
    @JsonBackReference
    private Island island;
    @Column(name = "x_coord", nullable = false)
    private Integer xCoord;
    @Column(name = "y_coord", nullable = false)
    private Integer yCoord;
    @Column(name = "base_rating", nullable = false)
    private Integer baseRating;
    @Column(name = "description",length = 1000)
    private String description;
    public Point() {

    }

    public Point(Island island, Integer xCoord, Integer yCoord, Integer baseRating, String description) {
        this.island = island;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
        this.baseRating = baseRating;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Island getIsland() {
        return island;
    }

    public void setIsland(Island island) {
        this.island = island;
    }

    public Integer getxCoord() {
        return xCoord;
    }

    public void setxCoord(Integer xCoord) {
        this.xCoord = xCoord;
    }

    public Integer getyCoord() {
        return yCoord;
    }

    public void setyCoord(Integer yCoord) {
        this.yCoord = yCoord;
    }

    public Integer getBaseRating() {
        return baseRating;
    }

    public void setBaseRating(Integer baseRating) {
        this.baseRating = baseRating;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
