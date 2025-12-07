package dk.ek.onidesign.catalog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ModulePageController {

    @GetMapping("/modules")
    public String showModulesPage() {
        return "modules.html"; // loader static/modules.html
    }
}