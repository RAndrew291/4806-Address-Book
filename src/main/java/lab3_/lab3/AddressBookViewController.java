package lab3_.lab3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view")
public class AddressBookViewController {

    @Autowired
    private AddressBookRepository addressBookRepository;

    // Display all address books
    @GetMapping("/addressBooks")
    public String listAddressBooks(Model model) {
        model.addAttribute("addressBooks", addressBookRepository.findAll());
        return "addressBooks";
    }

    // Display a specific address book with its buddies
    @GetMapping("/addressBooks/{name}")
    public String viewAddressBook(@PathVariable String name, Model model) {
        AddressBook addressBook = addressBookRepository.findByName(name);
        if (addressBook == null) {
            return "error";
        }
        model.addAttribute("addressBook", addressBook);
        return "addressBook-detail";
    }
}
