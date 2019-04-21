package com.astrology.web.astroweb.repositories;

import org.springframework.data.repository.CrudRepository;

import com.astrology.web.astroweb.domain.Product;

public interface ProductRepository extends CrudRepository<Product, Integer> {

}
