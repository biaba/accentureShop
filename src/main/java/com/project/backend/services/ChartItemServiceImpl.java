package com.project.backend.services;

import com.project.backend.models.ChartItem;
import com.project.backend.models.Product;
import com.project.backend.models.User;
import com.project.backend.repositories.ChartItemRepository;
import com.project.backend.repositories.ProductRepository;
import com.project.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ChartItemServiceImpl implements ChartItemService {

    @Autowired
    ChartItemRepository cartRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    ProductService productService;

    public List<ChartItem> listChartItems(User user) {
        List<ChartItem> cartItems = cartRepo.findByUser(user);
        for(ChartItem c: cartItems)        {
            productService.calculateDiscounts(c.getProduct());
        }
        return cartItems;
    }

    public ChartItem updateItem(User user, Product product, int quantity) {
        ChartItem cartItem = cartRepo.findByUserAndProduct(user, product);
        if(cartItem==null) {
            cartItem = new ChartItem();
            cartItem.setProduct(product);
            cartItem.setUser(user);
        }
        cartItem.setQuantity(quantity);
        cartItem = cartRepo.save(cartItem);
        return cartItem;
    }

    @Transactional
    public void deleteCartItem(User user, Product product) {
        cartRepo.deleteByUserAndProduct(user, product);
    }
}

