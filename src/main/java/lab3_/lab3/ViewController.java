package lab3_.lab3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class ViewController {

    private final AddressBookRepository addressBookRepository;

    public ViewController(AddressBookRepository repo){
        this.addressBookRepository = repo;
    }

    @GetMapping("/")
    public String index() { return "index"; }

    @GetMapping("/create")
    public String createForm() { return "createAddressBook"; }

    @GetMapping("/list")
    public String listBooks(Model model) {
        List<AddressBook> books = (List<AddressBook>) addressBookRepository.findAll();
        model.addAttribute("books", books);
        return "listAddressBook";
    }

    @GetMapping("/spa")
    public String spa() { return "spa"; } // create spa.html below
}
