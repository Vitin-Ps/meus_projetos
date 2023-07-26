package com.projetos.base.model.user;

import com.projetos.base.model.product.Product;

public record AlteracaoUser (Long id, String login, String password, UserRole role){
    public AlteracaoUser(User user){
        this(user.getId(), user.getLogin(), user.getPassword(), user.getRole());
    }
}
