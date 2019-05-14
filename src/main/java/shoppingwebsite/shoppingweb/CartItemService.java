package shoppingwebsite.shoppingweb;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartItemService {
    @Autowired
    private CartItemRepository repository;

    public void save(CartItem item){
        repository.save(item);
    }

    public List<CartItem> findAll(){
        return repository.findAll();
    }

    public List<CartItem> findByInCartTrue(){
        return repository.findByInCartTrue();
    }

    public void purchase(){
        List<CartItem> cartItems = findByInCartTrue();

        cartItems.stream().forEach(s-> s.setInCart(false));

        repository.saveAll(cartItems);
    }
}
