package com.project.backend.controllers;

import com.project.backend.models.*;
import com.project.backend.repositories.*;
import com.project.backend.services.ChartItemService;
import com.project.backend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

// This controller will be deleted. Created for testing Hibernate mappings only.
@Controller
public class TestController implements ServletContextAware {

    @Autowired
    DiscountRepository discountRepo;
    @Autowired
    ImageRepository imageRepo;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    PurchaseRepository purchaseRepo;
    @Autowired
    PurchasesProductsRepository ppRepo;
    @Autowired
    RoleRepository roleRepo;
    @Autowired
    UserRepository userRepo;
    @Autowired
    UserService userService;
    @Autowired
    ChartItemService cartService;
    @Autowired
    private ServletConfig servletConfig;
    @Autowired
    private ServletContext servletContext;
    @Autowired
    private ApplicationContext appContext;

    @GetMapping("/test/servlet/config")
    public ResponseEntity<String> getServletConfig() {
        return new ResponseEntity<String>("Servlet Config: " + servletConfig.getServletName(), HttpStatus.OK);
    }

    @GetMapping("/test/servlet/context")
    public ResponseEntity<String> getServletContext() {
        return new ResponseEntity<String>("Servlet Context: " + servletContext.getContextPath(), HttpStatus.OK);
    }

    // Getting all Beans from ApplicationContext
    @GetMapping("/rest")
    public String getBeans(Model model, HttpServletRequest request, HttpServletResponse response) {

        System.out.println(" csrf is in session: "+ request.getSession().getAttribute("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN"));

        CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
        System.out.println(" csrf is in Request Attribute: /request - in Header/" + token.getHeaderName()+" token /value/ "+token.getToken()+" and /response - in Parameter/ "+ token.getParameterName());

        System.out.println("Request attributes (one of them _csrf): "+request.getAttributeNames());
        Enumeration<String> it = request.getAttributeNames();
        while(it.hasMoreElements()){
            System.out.println("Request attribute: "+it.nextElement());
        }


        List<String> responseHeaderNames = (ArrayList<String>)response.getHeaderNames();
        for(String rhn: responseHeaderNames) {
            System.out.println("response header: "+rhn+" value: "+response.getHeader(rhn));
        }

        String[] names = appContext.getBeanDefinitionNames();
        List<String> l = new ArrayList<>();
        for(String name: names) {
            l.add(name.split("\\.")[name.split("\\.").length-1]);
        }
        Collections.sort(l);
        for(String ll: l) {
            System.out.println(l.indexOf(ll)+1+" "+ ll);
        }
        model.addAttribute("beans", l);
        return "beans";
    }

    @GetMapping("/test")
    public String test(Model model) {
        System.out.println("CONTROLLER");
        Discount dis = discountRepo.getById(1l);
        System.out.println(dis.getPercentage());
        dis.setPercentage(dis.getPercentage()+5);
        discountRepo.save(dis);
        dis=discountRepo.getById(1l);
        System.out.println("new discount is: "+dis.getPercentage());
        List<User> cust = userRepo.findAll();
        model.addAttribute("customers",cust);

        // Updating extra field in Associated Table (composite primary key + entity for associated table)
        Purchase pur = purchaseRepo.getById(3l);
        System.out.println("Purchase to be updated: " + pur);
        Product pr = productRepo.getById(1l);
        System.out.println("Product to be updated: "+ pr);
            // getting entity from with composite primary key
        PurchaseProductId id = new PurchaseProductId();
        id.setPurchaseId(3l);
        id.setProductId(1l);
        PurchasesProducts pp = ppRepo.getById(id);
            // changing amount
        pp.setAmount(4);
        System.out.println("Updated entity in associated table: " + pp);
        ppRepo.save(pp);

        // Getting extra field from associated table
        List<Product> topSoldList = productRepo.getTopProductsByCategory(Category.TOYS);
        for(Product p: topSoldList) {
            System.out.println("Product: "+ p.toString());
        }

        /*Image im = imageRepo.getById(1l);
        System.out.println(im.toString());

        Product prod = productRepo.getById(1l);
        System.out.println(prod.toString());

        Purchase pur = purchaseRepo.getById(1l);
        System.out.println(pur.toString());

        Role role = roleRepo.getById(1l);
        System.out.println(role.toString());

        User user = userRepo.getById(1l);
        System.out.println(user.toString());*/

        return "test";
    }

    // Playing around with/without CSRF protection
    @PostMapping("/test/go")
    public String getBeans(HttpServletRequest req) {
        System.out.println("Getting value from param: "+req.getParameter("val"));
        System.out.println("Getting value from param: "+req.getParameter("_csrf"));
        HttpSession session = req.getSession();
        Enumeration<String> it = session.getAttributeNames();
        while(it.hasMoreElements()) {
            System.out.println("Session "+ it.nextElement());
        }
        System.out.println("CSRF is in Session: "+ session.getAttribute("org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository.CSRF_TOKEN"));
        System.out.println("CSRF is in Request Attribute: " + req.getAttribute("_csrf"));
        System.out.println("CSRF is in Request Parameter: " + req.getParameter("_csrf"));

        return "home";
    }

    @GetMapping("/test/cart")
    public String cart() {
        // checking CartItem Repository
        Product p = productRepo.findById(2l).get();
        User u = userRepo.findById(3l).get();
        Random random = new Random();
        // Cart - updates or saves item
        // ChartItem cartItem = cartService.updateItem(u,p,random.nextInt(10));
        // Cart - deletes item from cart
        // cartService.deleteCartItem(u,p);
        // Cart - finds all user Items
        // List<ChartItem> l = cartService.listChartItems(u);
         return "testCart";
    }

    @GetMapping("/whoisloggedin")
    public String whoIsLoggedIn() {
        System.out.println("UserName of logged in user: " + userRepo.findByUserName(userService.loggedInUserName()).getUserName());
        System.out.println("Am I logged in: "+userService.isLoggedIn());
        System.out.println("HERE "+userRepo.getUserByEmail("aprily@inbox.lv"));
        System.out.println("Is my role 'customer': "+userService.hasRole("CUSTOMER"));
        System.out.println("");

        return "testCart";
    }

    @Override
    public void setServletContext(ServletContext servletContext) {

    }
}
