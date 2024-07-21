package org.tedygabrielmoisa.authenticationserver.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "pokemons")
public class Pokemon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 20)
    private String type1;

    @Column(length = 20)
    private String type2;

    @Column(length = 200)
    private String abilities;

    @Column(length = 10)
    private Integer hp;

    @Column(length = 10)
    private Integer attack;

    @Column(length = 10)
    private Integer defense;

    @Column(length = 10)
    private Integer specialAttack;

    @Column(length = 10)
    private Integer specialDefense;

    @Column(length = 10)
    private Integer speed;

    @Column(length = 2048)
    private String image;
}
