package lab3_.lab3;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class AddressBook {

    // added EAGER fetch type to immediately load all associated BuddyInfo entities
    // (previously only loaded when buddyInfo entities were accessed by which time the session was closed
    // causing a LazyInitializationException
    @OneToMany(mappedBy = "addressBook", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BuddyInfo> buddies = new ArrayList<>();

    @Id
    private String name;

    public AddressBook() {
    }

    public AddressBook(String name) {
        this.name = name;
    }

    public void addBuddie(BuddyInfo buddy) {
        buddies.add(buddy);
        buddy.setAddressBook(this);
    }

    public void removeBuddie(BuddyInfo buddy) {
        buddies.remove(buddy);
        buddy.setAddressBook(null);
    }

    public void setBuddies(List<BuddyInfo> buddies) {
        this.buddies = buddies;
        for (BuddyInfo buddy : buddies) {
            buddy.setAddressBook(this);
        }
    }

    public List<BuddyInfo> getBuddies() {
        return buddies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AddressBook{" + "name=" + name + ", buddies=" + buddies + '}';
    }

    public static void main(String[] args) {
        AddressBook ab = new AddressBook("My Address Book");
        ab.addBuddie(new BuddyInfo("Cameron", "911"));
        ab.addBuddie(new BuddyInfo("Clarke", "9028273050"));
        System.out.println(ab.getBuddies());
    }
}

// java -cp target/lab1-1.0-SNAPSHOT.jar intro.AddressBook

