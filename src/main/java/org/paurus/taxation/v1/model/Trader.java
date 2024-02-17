package org.paurus.taxation.v1.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "traders")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
