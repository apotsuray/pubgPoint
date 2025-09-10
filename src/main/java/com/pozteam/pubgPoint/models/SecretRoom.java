package com.pozteam.pubgPoint.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "secret_rooms")
public class SecretRoom {
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
    public SecretRoom() {

    }

    public SecretRoom(Island island, Integer xCoord, Integer yCoord) {
        this.island = island;
        this.xCoord = xCoord;
        this.yCoord = yCoord;
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
}
