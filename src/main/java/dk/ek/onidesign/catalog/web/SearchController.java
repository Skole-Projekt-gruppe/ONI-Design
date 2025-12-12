package dk.ek.onidesign.catalog.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

// Overvejes om den skal slettes.
public class SearchController
{

    @GetMapping("/Search")
    public String showModulesPage() {
        return "ModuleTable.html";
    }
}