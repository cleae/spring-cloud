package com.tl.commidity.eccommercecommodityservice.service;



import com.tl.commidity.eccommercecommodityservice.domain.Product;

import java.util.List;

public interface ProductService {

    List<Product> listProduct();

    Product findById(int id);


}
