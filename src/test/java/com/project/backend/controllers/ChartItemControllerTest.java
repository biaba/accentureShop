package com.project.backend.controllers;

import com.project.backend.models.*;
import com.project.backend.repositories.ChartItemRepository;
import com.project.backend.services.ChartItemService;
import com.project.backend.services.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.ui.Model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

// @WebMvcTest(ChartItemController.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChartItemControllerTest {

    @InjectMocks
    ChartItemController controller;

    @Mock
    UserService uService;
    @Mock
    ChartItemService cService;
    @Mock
    Model model;

    ChartItem item;
    List<ChartItem> items;
    User user;
    Product product;

    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @BeforeEach
    public void init() {
        user = createUser();
        product = createProduct();
        item = createCartItem();
        items = createCartItems();
    }

    @Test
    public void cartRepoCalledWithCorrectUser() {
        when(uService.loggedInUser()).thenReturn(user);
        when(cService.listChartItems(any(User.class))).thenReturn(items);
        controller.showShoppingCart(model);

        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        verify(cService).listChartItems(captor.capture());

        assertEquals(user, captor.getValue());
        assertEquals(controller.showShoppingCart(model), "shoppingcart");

    }

    private Set<Role> createRoles() {
        Set<Role> roles = new HashSet<>();
        roles.add(new Role("customer"));
        return roles;
    }

    private User createUser() {
        return new User("anna", "anna", "anna@anna.lv",
                "anna","ozola",null, createRoles());
    }

    private Product createProduct() {
        return new Product("car",new BigDecimal(23),"nice car",
                222, Category.TOYS,null, null, null);
    }

    private ChartItem createCartItem() {
        ChartItem c = new ChartItem();
        c.setId(7l);
        c.setUser(user);
        c.setProduct(product);
        c.setQuantity(222);
        return c;
    }
    private List<ChartItem> createCartItems() {
        List<ChartItem> items = new ArrayList<>();
        items.add(item);
        ChartItem c2 = createCartItem();
        c2.setId(8l);
        items.add(c2);
        return items;
    }


}