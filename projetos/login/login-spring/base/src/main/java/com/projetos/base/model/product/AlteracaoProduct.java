package com.projetos.base.model.product;

public record AlteracaoProduct(Long id, String name, Integer price) {
    public AlteracaoProduct(Product product){
        this(product.getId(), product.getName(), product.getPrice());
    }
}
