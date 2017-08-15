package com.example.stockup.repositories;

import com.example.stockup.models.Purchase;
import org.springframework.data.repository.CrudRepository;

public interface PurchaseRepo extends CrudRepository<Purchase,Long>
{
}
