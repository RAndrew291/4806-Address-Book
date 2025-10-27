package lab3_.lab3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/view")
public class AddressBookViewController {

    @Autowired
    private lab3_.lab3.AddressBookRepository addressBookRepository;

    @Autowired
    private BuddyRepository buddyRepository;

    // Serve the Single Page Application
    @GetMapping("/spa")
    public String spa() {
        return "addressBookSPA";
    }

    // Display all address books
    @GetMapping("/addressBooks")
    public String viewAllAddressBooks(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "addressBookDetail";
    }

    // Show form to create a new address book
    @GetMapping("/addressBooks/new")
    public String showCreateAddressBookForm(Model model) {
        model.addAttribute("addressBook", new lab3_.lab3.AddressBook());
        return "createAddressBook";
    }

    // Handle creating a new address book
    @PostMapping("/addressBooks/create")
    public String createAddressBook(@ModelAttribute lab3_.lab3.AddressBook addressBook) {
        addressBookRepository.save(addressBook);
        return "redirect:/view/addressBooks";
    }

    // View a specific address book with its buddies
    @GetMapping("/addressBooks/{name}")
    public String viewAddressBook(@PathVariable String name, Model model) {
        lab3_.lab3.AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook == null) {
            return "redirect:/view/addressBooks";
        }
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("newBuddy", new BuddyInfo());
        return "addressBookDetail";
    }

    // Show form to add a buddy to an address book
    @GetMapping("/addressBooks/{name}/addBuddy")
    public String showAddBuddyForm(@PathVariable String name, Model model) {
        lab3_.lab3.AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook == null) {
            return "redirect:/view/addressBooks";
        }
        model.addAttribute("addressBook", addressBook);
        model.addAttribute("buddy", new BuddyInfo());
        return "addBuddy";
    }

    // Handle adding a buddy to an address book
    @PostMapping("/addressBooks/{name}/addBuddy")
    public String addBuddy(@PathVariable String name, @ModelAttribute BuddyInfo buddy) {
        lab3_.lab3.AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook != null) {
            addressBook.addBuddy(buddy);
            addressBookRepository.save(addressBook);
        }
        return "redirect:/view/addressBooks/" + name;
    }

    // Delete an address book
    @PostMapping("/addressBooks/{name}/delete")
    public String deleteAddressBook(@PathVariable String name) {
        lab3_.lab3.AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook != null) {
            addressBookRepository.delete(addressBook);
        }
        return "redirect:/view/addressBooks";
    }

    // Delete a buddy from an address book
    @PostMapping("/addressBooks/{name}/deleteBuddy/{phone}")
    public String deleteBuddy(@PathVariable String name, @PathVariable String phone) {
        lab3_.lab3.AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook != null) {
            BuddyInfo buddy = buddyRepository.findByPhone(phone);
            if (buddy != null) {
                addressBook.removeBuddy(buddy);
                buddyRepository.delete(buddy);
                addressBookRepository.save(addressBook);
            }
        }
        return "redirect:/view/addressBooks/" + name;
    }
}