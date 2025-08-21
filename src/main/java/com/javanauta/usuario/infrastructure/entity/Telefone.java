package com.javanauta.usuario.infrastructure.entity;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name ="telefone")

public class Telefone {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Long id ;
    @Column(name="numero" , length = 10)
    private String numero ;
    @Column (name= "ddd" , length = 3)
    private String ddd ;
}
