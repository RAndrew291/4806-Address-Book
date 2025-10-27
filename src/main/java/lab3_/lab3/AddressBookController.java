package lab3_.lab3;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/addressBooks")
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyRepository buddyRepository;


    //Display all Address Books
    @GetMapping
    public Iterable<AddressBook> findAllAddressBooks() {
        return addressBookRepository.findAll();
    }

    //Get a specific address book by name
    @GetMapping("/{name}")
    public ResponseEntity<AddressBook> findAddressBookByName(@PathVariable String name) {
        AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addressBook);
    }

    //Create a new address book
    @PostMapping
    public ResponseEntity<AddressBook> createAddressBook(@RequestBody AddressBook addressBook) {
        AddressBook savedAddressBook = addressBookRepository.save(addressBook);
        return ResponseEntity.ok(savedAddressBook);
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteAddressBook(@PathVariable String name) {
        AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook != null) {
            addressBookRepository.delete(addressBook);
        }
        return ResponseEntity.noContent().build();
    }

    //Add buddy to address book
    @PostMapping("/{name}/contacts")
    public ResponseEntity<AddressBook> addBuddy(@PathVariable String name, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook == null) {
            return ResponseEntity.notFound().build();
        }
        addressBook.addBuddy(buddyInfo);
        AddressBook savedAddressBook = addressBookRepository.save(addressBook);
        return ResponseEntity.ok(savedAddressBook);
    }

    //remove specified buddy from specified book
    @DeleteMapping("/{name}/contacts/{buddyPhone}")
    public ResponseEntity<AddressBook> deleteBuddy(@PathVariable String name, @PathVariable String buddyPhone) {
        AddressBook addressbook = addressBookRepository.findByName(name);
        if (addressbook == null) {
            return ResponseEntity.notFound().build();
        }
        BuddyInfo buddy = buddyRepository.findByPhone(buddyPhone);
        if (buddy == null) {
            return ResponseEntity.notFound().build();
        }
        addressbook.removeBuddy(buddy);
        buddyRepository.delete(buddy);
        AddressBook saved = addressBookRepository.save(addressbook);
        return ResponseEntity.ok(saved);
    }

}