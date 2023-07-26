package com.projetos.base.model.product;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "product")
@Entity(name = "product")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Integer price;

    public Product(CadastroProduct data){
        this.price = data.price();
        this.name = data.name();
    }

    public void atualizaDados(AlteracaoProduct dados) {
        this.name = dados.name();
        this.price = dados.price();
    }
}