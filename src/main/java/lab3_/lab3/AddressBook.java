package lab3_.lab3;

/*
 *  AddressBook is a class that contains objects of class BuddyInfo into a
 *  convenient ArrayList. This allows their information to be called easily.
 */

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name = null;
    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL)
    private List<BuddyInfo> contacts = new ArrayList<>();
    private int size;

    /** Constructor for class AddressBook()
     */
    public AddressBook(String name) {
        this.name = name;
        size = 0;
    }


    /** Constructor for class AddressBook()
     */
    public AddressBook() {
    }

    /** Getter for addressbook ID
     *
     * @return addressbook id
     */
    public Long getId() {
        return id;
    }


    /** Setter for addressbook name
     *
     * @param name of the address book
     */
    public void  setName(String name) {
        this.name = name;
    }

    /** Getter for addressbook name
     *
     * @return name of the address book
     */
    public String getName() {
        return name;
    }


    /** Get the contacts list
     *
     */
    public List<BuddyInfo> getContacts() {
        return contacts;
    }

    /** addBuddy method adds a buddy to the array
     * list created by the constructor
     * @param buddy a BuddyInfo object
     */
    public void addBuddy(BuddyInfo buddy){
        /* Adds a buddy to the contacts list */
        if(buddy != null) {
            contacts.add(buddy);
            buddy.setAddressBook(this);
        }
        size = contacts.size();

    }

    /** removeBuddy method removes a buddy from the addressBook
     *
     * @param buddy a BuddyInfo object
     */
    public void removeBuddy(BuddyInfo buddy){
        contacts.remove(buddy);
        size = contacts.size();
    }
    /** printContacts provides a method to print all contacts in the addressBook.
     *
     */
    public String organizeContacts(){
        String contactsString = "";
        for (BuddyInfo contact : this.contacts) {
            if (contact.getName() != null) {
                contactsString += "Name: " + contact.getName() + ", Phone: " + contact.getPhone() + "\n";
            }
        }
        return contactsString;
    }

    public void printContacts(){
        System.out.println("Contacts:\n" + organizeContacts());
    }

    /** Returns the number of contacts in the address book
     *
     * @return the size of the address book
     */
    public int getBookSize(){
        int size = 0;
        for (BuddyInfo contact : this.contacts) {
            if (contact.getName() != null) {
                size += 1;
            }
        }
        return size;
    }

    @Override
    public String toString() {
        String overview = "";
        overview += "AddressBook[Name: " + name + ", Size: " + size +", ID: " + id + "]\n";
        return overview;
    }
}
