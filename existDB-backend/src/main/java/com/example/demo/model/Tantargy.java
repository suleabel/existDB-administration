package com.example.demo.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@Entity
@Table(name="tanterv")
public class Tantargy implements Serializable {

    @Id
    @Column
    private String Targykod;
    @Column
    private String Felev;
    @Column
    private String Targynev;
    @Column
    private String Targy_angol_neve;
    @Column
    private String Ea;
    @Column
    private String Gy;
    @Column
    private String Kov;
    @Column
    private String Kr;
    @Column
    private String ETF;
    @Column
    private String helyettesitotargy_kodja;
}