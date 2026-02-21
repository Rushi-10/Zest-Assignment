package com.zestt.assign.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.zestt.assign.Entity.Product;



@Repository
public interface ProductRepository extends JpaRepository<Product, Integer>{

}
