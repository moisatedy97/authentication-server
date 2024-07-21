package org.tedygabrielmoisa.authenticationserver.entities;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity class representing a Pokemon.
 */
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Table(name = "pokemons")
public class Pokemon {

    /**
     * The unique identifier for the Pokemon.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * The name of the Pokemon.
     */
    @Column(nullable = false, length = 50)
    private String name;

    /**
     * The primary type of the Pokemon.
     */
    @Column(length = 20)
    private String type1;

    /**
     * The secondary type of the Pokemon.
     */
    @Column(length = 20)
    private String type2;

    /**
     * The abilities of the Pokemon.
     */
    @Column(length = 200)
    private String abilities;

    /**
     * The hit points (HP) of the Pokemon.
     */
    @Column(length = 10)
    private Integer hp;

    /**
     * The attack stat of the Pokemon.
     */
    @Column(length = 10)
    private Integer attack;

    /**
     * The defense stat of the Pokemon.
     */
    @Column(length = 10)
    private Integer defense;

    /**
     * The special attack stat of the Pokemon.
     */
    @Column(length = 10)
    private Integer specialAttack;

    /**
     * The special defense stat of the Pokemon.
     */
    @Column(length = 10)
    private Integer specialDefense;

    /**
     * The speed stat of the Pokemon.
     */
    @Column(length = 10)
    private Integer speed;

    /**
     * The image URL or path of the Pokemon.
     */
    @Column(length = 2048)
    private String image;
}
