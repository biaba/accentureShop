package com.project.backend.controllers;

import com.project.backend.mappers.UserMapper;
import com.project.backend.models.*;
import com.project.backend.models.modelsFront.UserFront;
import com.project.backend.repositories.ImageRepository;
import com.project.backend.services.ChartItemService;
import com.project.backend.services.DiscountService;
import com.project.backend.services.ProductService;
import com.project.backend.services.UserService;
import com.project.backend.utils.UserExcelExporter;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class ManagerController {

    @Autowired
    ImageRepository imageRepo;
    @Autowired
    ProductService productService;
    @Autowired
    UserService userService;
    @Autowired
    DiscountService discountService;
    @Autowired
    ChartItemService cartService;
    @Autowired
    UserMapper userMapper;

    // Gets a list of all customers
    @GetMapping("/manager/customers")
    public String allCustomers(Model model) {
        List<User> customers = userService.findAllUsers();
        model.addAttribute("customers", customers);
        return "customers";
    }

    // Sort customers by total money spent
    @GetMapping("/manager/customers/top/amount")
    public String customersTopSale(Model model) {
        List<UserFront> customers = new ArrayList<>();
        List<UserActivity> ua = userService.findAllUsersByMoneySpent();
        for(UserActivity u: ua){
            customers.add(userMapper.getUserFrontFromUserActivity(u));
        }
        model.addAttribute("customers", customers);
        model.addAttribute("addInfo", true);
        return "customers";
    }
    // Sort customers by purchase frequency
    @GetMapping("/manager/customers/top/freq")
    public String customersPurchaseFreq(Model model) {
        List<UserActivity> customers = userService.findAllUsersByPurchaseFrequency();
        model.addAttribute("customers", customers);
        model.addAttribute("addInfo",true);
        return "customers";
    }

/*    // Change customer status (from/to loyal customer)
    @PostMapping(value = "/manager/customer/loyalty")
    public String addDiscountToProducts(@ModelAttribute("User") User user, BindingResult br, Model model) throws Exception {
        userService.changeCustomerLoyalty(user.getId(), user.getRole());
        List<Product> updatedProducts = productService.findByCategory(products.get(0).getCategory().toString());

        productList.setProducts(updatedProducts);
        model.addAttribute("Products", productList);
        List<Discount> discounts = discountService.getAllDiscounts();
        model.addAttribute("listOfDiscounts", discounts);

        return "customers";
    }*/

    @GetMapping("/manager/customers/export/excel")
    public void exportToExcel(HttpServletResponse response) throws IOException {
        response.setContentType("application/octet-stream");
        DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
        String currentDateTime = dateFormatter.format(new Date());

        String headerKey = "Content-Disposition";
        String headerValue = "attachment; filename=users_" + currentDateTime + ".xlsx";
        response.setHeader(headerKey, headerValue);

        List<User> listUsers = userService.findAllUsers();

        UserExcelExporter excelExporter = new UserExcelExporter(listUsers);

        excelExporter.export(response);
    }

    @GetMapping("/manager/createproduct")
    public String showUploadForm() {
        return "imageup";
    }

    @PostMapping("/manager/upload")
    public String uploadImage(@RequestParam MultipartFile image, Model model) throws Exception {
        if (image != null) {
            System.out.println("Saving file: " + image.getOriginalFilename());
            Image uploadFile = new Image();
            uploadFile.setProduct(productService.findProductById(2l));
            uploadFile.setName(image.getOriginalFilename());
            uploadFile.setContent(image.getBytes());
            Long id = imageRepo.save(uploadFile).getId();
            model.addAttribute("id", id);
        }
        return "imageup";
    }

    @GetMapping(value = "/manager/product/{id}")
    public void getProductById(HttpServletResponse response, @PathVariable("id") Long id) throws Exception {
        response.setContentType("image/jpeg");

        byte[] bytes = imageRepo.getById(id).getContent();
        InputStream inputStream = new ByteArrayInputStream(bytes);
        IOUtils.copy(inputStream, response.getOutputStream());
    }

    // Creates a product
    @PostMapping("/manager/products/add")
    public String createProduct(@PathVariable("id") Long id, Model model) {
        return "home";
    }

    // Creates a discount
    @PostMapping("/manager/discounts/add")
    public String createDiscount(@PathVariable("id") Long id, Model model) {

        return "home";
    }

    // Adds discount to particular products
    @PostMapping(value = "/manager/products/adddiscount")
    public String addDiscountToProducts(@ModelAttribute("Products") ProductListContainer productList, BindingResult br, Model model) throws Exception {
        List<Product> products = productList.getProducts();
        productService.addDiscountToProducts(products);
        List<Product> updatedProducts = productService.findByCategory(products.get(0).getCategory().toString());

        productList.setProducts(updatedProducts);
        model.addAttribute("Products", productList);
        List<Discount> discounts = discountService.getAllDiscounts();
        model.addAttribute("listOfDiscounts", discounts);

        return "products";
    }

    // Removes discount from a particular product
    @PostMapping(value = "/manager/discounts/remove/{id}")
    public String removeDiscountFromProduct(@PathVariable("id") Long id, Model model){
        productService.removeDiscountFromProduct(id);

        List<Product> products = productService.findByCategory(productService.findProductById(id).getCategory().toString());
        ProductListContainer productList = new ProductListContainer();
        productList.setProducts(products);
        model.addAttribute("Products", productList);
        List<Discount> discounts = discountService.getAllDiscounts();
        model.addAttribute("listOfDiscounts", discounts);
        return "products";
    }

}