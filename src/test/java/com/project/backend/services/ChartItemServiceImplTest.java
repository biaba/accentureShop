package com.project.backend.services;

import com.project.backend.models.*;
import com.project.backend.repositories.ChartItemRepository;
import com.project.backend.repositories.ProductRepository;
import com.project.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ChartItemServiceImplTest {

    @InjectMocks
    ChartItemServiceImpl service;
    @Mock
    ChartItemRepository cartRepo;
    @Mock
    ProductRepository productRepo;
    @Mock
    UserRepository userRepo;
    @Mock
    ProductService productService;

    private List<ChartItem> items;
    private ChartItem item;
    private User user;
    private Product product;

    @BeforeAll
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
    void listCartItems() {
        when(cartRepo.findByUser(any(User.class))).thenReturn(items);
        doNothing().when(productService).calculateDiscounts(item.getProduct());
        List<ChartItem> retrievedItems = service.listChartItems(new User());
        assertEquals(2,retrievedItems.size());
    }

    // testing passed arguments
    @Test
    void correctArgumentIsPassedToFindCartItems() {
        when(cartRepo.findByUser(any(User.class))).thenReturn(items);
        User u = user;
        service.listChartItems(u);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

        verify(cartRepo).findByUser(captor.capture());
        assertEquals(u, captor.getValue());
    }

    @Test
    void updateCartQuantityWhenItemExists() {
        item.setQuantity(33);
        when(cartRepo.findByUserAndProduct(any(User.class), any(Product.class))).thenReturn(item);
        when(cartRepo.save(any(ChartItem.class))).thenReturn(item);
        ChartItem updatedItem = service.updateItem(user, product, 33);
        assertEquals(updatedItem, item);
    }

    @Test
    void updateCartItemWhenItemNotExists() {
        when(cartRepo.findByUserAndProduct(any(User.class), any(Product.class))).thenReturn(null);
        when(cartRepo.save(any(ChartItem.class))).thenReturn(item);
        ChartItem createdItem = service.updateItem(user, product, 222);
        assertEquals(createdItem, item);
    }

    @Test
    void correctCartItemDeleted() {
        service.deleteCartItem(user, product);
        ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);
        ArgumentCaptor<Product> captor2 = ArgumentCaptor.forClass(Product.class);
        verify(cartRepo).deleteByUserAndProduct(captor.capture(), captor2.capture());
        assertEquals(user, captor.getValue());
        assertEquals(product, captor2.getValue());
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
