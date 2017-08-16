package com.example.stockup.controllers;

import com.example.stockup.models.Product;
import com.example.stockup.models.Purchase;
import com.example.stockup.repositories.ProductRepo;
import com.example.stockup.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Iterator;

@Controller
public class MainController
{
    @Autowired
    ProductRepo prodRepo;
    @Autowired
    PurchaseRepo purchaseRepo;

    @GetMapping("/")
    public String start()
    {
        return "index";
    }

    @GetMapping("/index")
    public String home()
    {
        return "index";
    }

    @GetMapping("/newproduct")
    public String newProduct(Model model)
    {
        model.addAttribute("newproduct", new Product());
        return "newproduct";
    }

    @PostMapping("/newproduct")
    public String productSubmit(@Valid @ModelAttribute("newproduct") Product product, BindingResult bindingResult)
    {
        System.out.println(product.getProductId());

        if(bindingResult.hasErrors())
        {
            return "newproduct";
        }
        prodRepo.save(product);
        return "shownewprod";
    }

    @GetMapping("/update/{id}")
    public String updateProduct(@PathVariable("id") long id, Model model){
        System.out.println(id);
        model.addAttribute("newproduct", prodRepo.findOne(id));

        return "newproduct";
    }

    @GetMapping("/showquantities")
    public String showStock(Model model)
    {
        Iterable <Product> prodList = prodRepo.findAll();
        model.addAttribute("prods", prodList);
        return "showquantities";
    }

    @GetMapping("/makepurchase")
    public String makePurchase(Model model)
    {
        model.addAttribute("productbought", new Purchase());
        return "makepurchase";
    }

    @PostMapping("/makepurchase")
    public String printReceipt(@ModelAttribute ("productbought") Purchase purchase, Model model)
    {
        System.out.println("ID: "+purchase.getProductId());

        if(prodRepo.exists(purchase.getProductId()))
        {
            Product prod = prodRepo.findOne(purchase.getProductId());
            purchase.setProductName(prod.getProductName());
            purchase.setCost(prod.getPrice()*purchase.getQuantity()*1.06);
            //change amount in prodRepo
            prod.setQuantity(prod.getQuantity()-purchase.getQuantity());
            //prodRepo.save(prod);
        }

        purchaseRepo.save(purchase);
        model.addAttribute("item",purchase);
        return "printreceipt";
    }

    @GetMapping("/showsales")
    public String showSales(Model model)
    {
        Iterable <Purchase> purchases = purchaseRepo.findAll();
        model.addAttribute("purchases",purchases);
        return "showsales";

    }
}
