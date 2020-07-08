/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.baitaplon.pojo;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Liem Nemo
 */
@Entity
@Table(name = "halls")
public class Hall implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    private String image;
    private int maximum_tables;
    private int minimum_tables;
    private BigDecimal price;

    @Override
    public String toString() {
        return String.valueOf(this.id);
    }

    @Override
    public boolean equals(Object object) {
        return (object instanceof Hall) && (id != null)
                ? id.equals(((Hall) object).id)
                : (object == this);
    }

//    @Override
//    public boolean equals(Object obj) {
//        Hall h = (Hall) obj;
//        return this.id == h.id;
//    }
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 29 * hash + this.id;
        return hash;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the image
     */
    public String getImage() {
        return image;
    }

    /**
     * @param image the image to set
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * @return the maximum_tables
     */
    public int getMaximum_tables() {
        return maximum_tables;
    }

    /**
     * @param maximum_tables the maximum_tables to set
     */
    public void setMaximum_tables(int maximum_tables) {
        this.maximum_tables = maximum_tables;
    }

    /**
     * @return the minimum_tables
     */
    public int getMinimum_tables() {
        return minimum_tables;
    }

    /**
     * @param minimum_tables the minimum_tables to set
     */
    public void setMinimum_tables(int minimum_tables) {
        this.minimum_tables = minimum_tables;
    }

    /**
     * @return the price
     */
    public BigDecimal getPrice() {
        return price;
    }

    /**
     * @param price the price to set
     */
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
