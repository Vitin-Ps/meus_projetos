package com.example.mybarbearia.model.carrinhodecompras;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Table(name = "carrinho_de_compras")
@Entity(name = "CarrinhoDeCompras")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class CarrinhoDeCompras {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long idCliente;
    private Long idItem;
    @Enumerated(EnumType.STRING)
    private TipoItem tipoItem;
    private BigDecimal preco;

}
