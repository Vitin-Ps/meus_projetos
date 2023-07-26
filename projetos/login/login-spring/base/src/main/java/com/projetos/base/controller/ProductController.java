package com.projetos.base.controller;

import com.projetos.base.model.product.AlteracaoProduct;
import com.projetos.base.model.product.CadastroProduct;
import com.projetos.base.model.product.Product;
import com.projetos.base.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("product")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("/")
    public String pagProduct(Model model, Long id) {
        if(id != null) {
            var users = repository.getReferenceById(id);
            model.addAttribute("product", users);
        }
        model.addAttribute("products", repository.findAll());
        return "product";
    }

    @PostMapping("/cadProduct")
    @Transactional
    public String cadProduct(CadastroProduct dados) {
        var product = new Product(dados);
        repository.save(product);
        return "redirect:/";
    }

    @GetMapping("/delProduct")
    public String delProduct(Long id) {
        repository.deleteById(id);
        return "redirect:/";
    }

    @PutMapping("/cadProduct")
    @Transactional
    public String altProduct(AlteracaoProduct dados) {
        var product = repository.getReferenceById(dados.id());
        product.atualizaDados(dados);
        return "redirect:/";
    }

}
