package dk.ek.onidesign.catalog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SearchController
{

    @GetMapping("/Search")
    public String showModulesPage() {
        return "ModuleTable.html";
    }
}