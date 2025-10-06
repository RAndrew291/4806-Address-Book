package lab3_.lab3;

import jakarta.persistence.*;

@Entity
public class BuddyInfo {

    private String name;
    @Id
    private String phone;
    @ManyToOne
    private AddressBook addressBook;

    public BuddyInfo() {
        this.name = "";
        this.phone = "";
    }

    public BuddyInfo(String name, String phone) {
        this.name = name;
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public String toString() {
        return name + ": " + phone;
    }
}

