package lab3_.lab3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/addressBooks")
public class AddressBookController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyInfoRepository buddyInfoRepository;

    // Get all addressBooks
    @GetMapping
    public Iterable<AddressBook> findAllAddressBooks() {
        return addressBookRepository.findAll();
    }

    // Get addressBook of specified name
    @GetMapping("/{name}")
    public ResponseEntity<AddressBook> findAddressBookByName(@PathVariable String name) {
        AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(addressBook);
    }

    // create new address book
    @PostMapping
    public ResponseEntity<AddressBook> createAddressBook(@RequestBody AddressBook addressBook) {
        AddressBook saved = addressBookRepository.save(addressBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // delete specified address book
    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteAddressBook(@PathVariable String name) {
        AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook != null) {
            addressBookRepository.delete(addressBook);
        }
        return ResponseEntity.noContent().build();
    }

    // add buddy to specified address book
    @PostMapping("/{name}/buddies")
    public ResponseEntity<AddressBook> addBuddy(@PathVariable String name, @RequestBody BuddyInfo buddyInfo) {
        AddressBook addressbook = addressBookRepository.findByName(name);
        if (addressbook == null) {
            return ResponseEntity.notFound().build();
        }
        addressbook.addBuddie(buddyInfo);
        AddressBook saved = addressBookRepository.save(addressbook);
        return ResponseEntity.ok(saved);
    }

    // remove specified buddy from specified address book
    @DeleteMapping("/{name}/buddies/{buddyPhone}")
    public ResponseEntity<AddressBook> deleteBuddy(@PathVariable String name, @PathVariable String buddyPhone) {
        AddressBook addressbook = addressBookRepository.findByName(name);
        if (addressbook == null) {
            return ResponseEntity.notFound().build();
        }
        BuddyInfo buddy = buddyInfoRepository.findByPhone(buddyPhone);
        if (buddy == null) {
            return ResponseEntity.notFound().build();
        }
        addressbook.removeBuddie(buddy);
        buddyInfoRepository.delete(buddy);
        AddressBook saved = addressBookRepository.save(addressbook);
        return ResponseEntity.ok(saved);
    }
}
