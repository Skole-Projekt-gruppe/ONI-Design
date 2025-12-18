package dk.ek.onidesign.catalog.web;

import dk.ek.onidesign.catalog.dto.DashboardDto;
import dk.ek.onidesign.catalog.service.DashboardService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DashboardRestController {

    private final DashboardService dashboardService;

    public DashboardRestController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping("/api/dashboard")
    public DashboardDto getDashboard(@RequestParam(defaultValue = "1") Long moduleId) {
        return dashboardService.getModuleDashboard(moduleId);
    }
}
