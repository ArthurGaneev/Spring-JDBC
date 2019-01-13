package ru.itpark.webstore.service;

import org.springframework.stereotype.Service;
import ru.itpark.webstore.domain.Product;
import ru.itpark.webstore.exception.ProductNotFoundException;
import ru.itpark.webstore.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public List<Product> getAll() {
        return repository.getAll();
    }

    public Product getById(int id) {
        return repository.getById(id)
                .orElseThrow(ProductNotFoundException::new);
    }

    public Product getByIdOrEmpty(int id) {
        return repository.getById(id)
                .orElse(new Product());
    }

    public void save(Product item) {
        repository.save(item);
    }

    public void removeById(int id) {
        repository.removeById(id);
    }
}
