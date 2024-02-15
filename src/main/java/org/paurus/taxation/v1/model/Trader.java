package org.paurus.taxation.v1.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "traders")
@Data
public class Trader {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    long id;
    @Column(name = "name")
    String name;
    @Column(name = "country_code")
    String countryCode;
}
