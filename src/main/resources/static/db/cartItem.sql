create table if not exists cart_item(
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    product_id INT NOT NULL,
    name VARCHAR(50),
    quantity INT NOT NULL,
    in_cart boolean NOT NULL default 1,
    foreign key(product_id) references products_list(id)
) engine=InnoDB;