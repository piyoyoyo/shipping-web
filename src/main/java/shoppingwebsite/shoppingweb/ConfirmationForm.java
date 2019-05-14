package shoppingwebsite.shoppingweb;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;

public class ConfirmationForm {

    @NotBlank(message = "必須項目です")
    private String name;

    @NotBlank(message = "必須項目です")
    private String address;

    @NotBlank(message = "必須項目です")
    @Digits(integer = 10, fraction = 11, message = "10桁か11桁の数字を入力してください")
    private String phone;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
