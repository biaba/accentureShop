package com.project.backend.controllers;

import com.project.backend.models.ChartItem;
import com.project.backend.models.Product;
import com.project.backend.models.User;
import com.project.backend.services.ChartItemService;
import com.project.backend.services.ProductService;
import com.project.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class ChartItemController {

    @Autowired
    ChartItemService chartService;
    @Autowired
    UserService userService;
    @Autowired
    ProductService productService;

    @GetMapping("/cart")
    public String showShoppingCart(Model model) {
        User user = userService.loggedInUser();
        List<ChartItem> cartItems= chartService.listChartItems(user);
        System.out.println(" I am : "+ user.getUserName());
        System.out.println(" My first item is: "+ (cartItems.size()>0 ? cartItems.get(0).getProduct().getTitle():""));
        model.addAttribute("cartItems", cartItems);
        return "shoppingcart";
    }
    @PostMapping("/cart/addproduct/{id}")
    public String addProductToCart(@PathVariable("id") Long id, Model model) {
        User user = userService.loggedInUser();
        Product product = productService.findProductById(id);
        chartService.addCartItem(product, 1, user);
        List<ChartItem> cartItems= chartService.listChartItems(user);
        System.out.println(" I am : "+ user.getUserName());
        System.out.println(" My first item is: "+ cartItems.get(0).getProduct().getTitle());
        model.addAttribute("cartItems", cartItems);
        return "shoppingcart";
    }

    @PostMapping("/cart/deleteproduct/{id}")
    public String deleteProductFromCart(@PathVariable("id") Long id, Model model) {
        User user = userService.loggedInUser();
        Product product = productService.findProductById(id);
        //chartService.deleteCartItem(product, 1, user);
        List<ChartItem> cartItems= chartService.listChartItems(user);
        System.out.println(" I am : "+ user.getUserName());
        System.out.println(" My first item is: "+ cartItems.get(0).getProduct().getTitle());
        model.addAttribute("cartItems", cartItems);
        return "shoppingcart";
    }

  /*  @PostMapping("/cart/addproduct/{id}")
    public String addProductToCart(@PathVariable("id") Long id, Model model) {
        User user = userService.loggedInUser();
        Product product = productService.findProductById(id);
        cartService.addCartItem(product, 1, user);
       *//* List<ChartItem> cartItems= chartService.listChartItems(user);
        System.out.println(" I am : "+ user.getUserName());
        System.out.println(" My first item is: "+ cartItems.get(0).getProduct().getTitle());
        model.addAttribute("cartItems", cartItems);*//*
        return "home";
    }*/
}
