package com.gogil.sellercrm.adapter.web;

import com.gogil.sellercrm.adapter.security.UserDetailsImpl;
import com.gogil.sellercrm.domain.user.Role;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        if (userDetails.getUser().getRole() == Role.ADMIN) {
            return "redirect:/dashboard";
        }
        return "redirect:/my/profile";
    }
}
