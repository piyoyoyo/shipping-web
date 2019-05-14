package shoppingwebsite.shoppingweb;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class CartItem {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private Long productId;

    private int quantity;

    private boolean inCart;

    public CartItem() {

    }

    public CartItem(Long productId, int quantity, boolean inCart) {
        this.productId = productId;
        this.quantity = quantity;
        this.inCart = inCart;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public boolean isInCart() {
        return inCart;
    }

    public void setInCart(boolean inCart) {
        this.inCart = inCart;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
