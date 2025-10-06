package lab3_.lab3;

import jakarta.persistence.*;

@Entity
public class BuddyInfo {

    private String name;
    @Id
    private String phone;
    private String address;

    @ManyToOne
    private AddressBook addressBook;

    public BuddyInfo() {
        this.name = "";
        this.phone = "";
        this.address = "";
    }

    public BuddyInfo(String name, String phone) {
        this.name = name;
        this.phone = phone;
        this.address = "";
    }

    public BuddyInfo(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public AddressBook getAddressBook() {
        return addressBook;
    }

    public void setAddressBook(AddressBook addressBook) {
        this.addressBook = addressBook;
    }

    @Override
    public String toString() {
        return name + ": " + phone + " (" + address + ")";
    }
}