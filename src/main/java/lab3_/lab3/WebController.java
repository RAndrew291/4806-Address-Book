package lab3_.lab3;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Root controller to redirect users to the main application view
 */
@Controller
public class WebController {

    /**
     * Redirect root URL to the address books view
     */
    @GetMapping("/")
    public String root() {
        return "redirect:/view/addressBooks";
    }

    /**
     * Alternative home mapping
     */
    @GetMapping("/home")
    public String home() {
        return "redirect:/view/addressBooks";
    }

    /**
     * Index mapping
     */
    @GetMapping("/index")
    public String index() {
        return "redirect:/view/addressBooks";
    }
}