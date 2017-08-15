package com.example.stockup.controllers;

import com.example.stockup.models.Product;
import com.example.stockup.models.Purchase;
import com.example.stockup.repositories.ProductRepo;
import com.example.stockup.repositories.PurchaseRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

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
        if(bindingResult.hasErrors())
        {
            return "newproduct";
        }
        prodRepo.save(product);
        return "shownewprod";
    }

    @GetMapping("/showquantities")
    public String showStock(Model model)
    {
        Iterable <Product> prods = prodRepo.findAll();
        model.addAttribute("prods", prods);
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

        Iterator<Product> prods = prodRepo.findAll().iterator();
        while(prods.hasNext())
        {
            Product prod=prods.next();
            if(prod.getProductId().equalsIgnoreCase(purchase.getProductId()))
            {
                purchase.setProductName(prod.getProductName());
                purchase.setCost(prod.getPrice()*purchase.getQuantity()*1.06);
                //change amount in prodRepo
                prod.setQuantity(prod.getQuantity()-purchase.getQuantity());
                prodRepo.save(prod);
            }
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
