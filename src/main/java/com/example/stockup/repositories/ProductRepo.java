package com.example.stockup.repositories;

import com.example.stockup.models.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepo extends CrudRepository<Product,Long>
{
}
