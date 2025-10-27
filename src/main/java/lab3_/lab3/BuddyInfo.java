package lab3_.lab3;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

/**
 *  BuddyInfo is a class that creates a BuddyInfo object that stores name,
 *  address and phone numbers of an individual.
 */
@Entity
public class BuddyInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name = null;
    private String phone = null;
    private String address = null;

    @ManyToOne
    @JsonIgnore
    private lab3_.lab3.AddressBook addressBook;

    /** Lab 1 Constructor of BuddyInfo Object
     *
     * @param name buddy name
     * @param phone buddy phone number
     */
    public BuddyInfo(String name, String phone, String address) {
        this.name = name;
        this.phone = phone;
        this.address = address;
    }

    /**
     * Default Constructor for BuddyInfo object for JPA use
     */
    protected BuddyInfo() {}

    /** Set name of buddy
     *
     * @param name The name of the buddy
     */
    public void setName(String name) {this.name = name;}

    /** Get name of buddy
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * Set address of buddy
     */
    public void setAddress(String address) {this.address = address;}

    /**
     * Get address of buddy
     * @return address the String address of the buddy
     */
    public String getAddress() {return address;}

    /** Set the address book of the buddy
     * @param addressBook the address book
     */
    public void setAddressBook(lab3_.lab3.AddressBook addressBook) {this.addressBook = addressBook;}

    /** Getter for address book
     *
     * @return addressbook
     */
    public lab3_.lab3.AddressBook getAddressBook() {return addressBook;}

    /** Setter for buddy phone number
     *
     * @param phone the buddy's phone num
     */
    public void setPhone(String phone) {this.phone = phone;}

    /** Getter for phone number
     *
     * @return phone number associated with buddy
     */
    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return("BuddyInfo[Name: " + name + ", Phone: " + phone + ", ID: " + id +" Address: " + address + "]\n");
    }

    /** Getter for the id of the buddy
     *
     * @return id the buddy ID
     */
    public Long getId(){
        return id;
    }


}