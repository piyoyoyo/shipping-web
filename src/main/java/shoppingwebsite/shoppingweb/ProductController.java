package shoppingwebsite.shoppingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    @Autowired
    ProductService productService;

    @Autowired
    CartItemService cartItemService;

    @GetMapping
    public String index(Model model){
        List<Product> productList = productService.findAll();
        model.addAttribute("productsList", productList);
        return "index";
    };

    @GetMapping("{id}")
    public String showDetail(@PathVariable Long id, Model model){
        Optional<Product> optProduct = productService.findById(id);
        try{
            Product product = optProduct.orElseThrow(() -> new RuntimeException());
            model.addAttribute("product", product);
            return "detail";

        }catch (RuntimeException e){
            e.printStackTrace();
            return "noproduct";

        }
    }


    @PostMapping("/putincart")
    public String putIntCart(@RequestParam(name = "id") String id, Model model){
        Long productId = Long.valueOf(id);

        List<CartItem> cartItems = cartItemService.findByInCartTrue();

        boolean alreadyInCart = false;

        for(CartItem cartItem : cartItems){
            //カートに同じ商品がある場合
            if(productId == cartItem.getProductId()){
                cartItem.setQuantity(cartItem.getQuantity()+1);
                cartItemService.save(cartItem);
                alreadyInCart = true;
                break;
            }
        }
        //カートに同じ商品がない場合
        if(alreadyInCart == false){
            CartItem newItem = new CartItem(productId, 1, true);
            cartItemService.save(newItem);
        }
        return cart(model);

    }


    @GetMapping("/cart/{id}")
    public String cart(Model model){


        List<CartItem> fixedCartItems = cartItemService.findByInCartTrue();
        List<CartForm> cartForms = new ArrayList<>();

        List<Product> products = productService.findAll();

        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(
                s->s.getId(),
                s->s
        ));

        for(CartItem item : fixedCartItems){
            Long productId = item.getProductId();
            Product product = productMap.get(productId);
            CartForm cartForm = new CartForm(product.getId(), product.getName(), product.getPrice(), item.getQuantity());
            cartForms.add(cartForm);
        }

        model.addAttribute("cartForms", cartForms);
        double sum = cartForms.stream().mapToDouble(c -> c.getQuantity()*c.getPrice()).sum();

        model.addAttribute("sum", sum);

        return "cart";
    }
    @GetMapping("/inputconfirm")
    public String inputConfirm(ConfirmationForm form, Model model){
        return "input-confirm";
    }

    @PostMapping("/showconfirm")
    public String showConfirm(@Validated @ModelAttribute("confirmationForm") ConfirmationForm form, BindingResult result, Model model){

        if (result.hasErrors()) {
            model.addAttribute("validationError", "値が入力されていません");
            return inputConfirm(form, model);
        }

        return "confirm";
    }

    @GetMapping("/purchase")
    public String purchase(){
        cartItemService.purchase();

        return "done";
    }


}
