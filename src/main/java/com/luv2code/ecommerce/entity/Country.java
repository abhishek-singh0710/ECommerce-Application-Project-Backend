package com.luv2code.ecommerce.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name= "country")
@Getter
@Setter
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id")
    private int id;

    @Column(name= "code")
    private String code;

    @Column(name= "name")
    private String name;

    // Set up OneToMany relationship with States
    @OneToMany(mappedBy = "country")
    @JsonIgnore                    // This will ignore this states data and not provide it in the JSON Data for the API Endpoint localhost:8080/api/countries
    private List<State> states;
}
