package softuni.residentevil.web.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;
import java.util.stream.Collectors;

@Controller
public class HomeController extends BaseController{
    @GetMapping("/")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView, Authentication principal){
        modelAndView.addObject("user", principal.getName());
        modelAndView.addObject("role",principal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
        return super.view("home",modelAndView);
    }

    @GetMapping("/index")

    public ModelAndView index(ModelAndView modelAndView, Authentication principal, HttpSession session){
        modelAndView.addObject("user", principal.getName());
        return super.view("index",modelAndView);
    }

    @GetMapping("/home")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView home(ModelAndView modelAndView, Authentication principal, HttpSession session){
        modelAndView.addObject("user", principal.getName());
        modelAndView.addObject("role",principal.getAuthorities()
                .stream().map(GrantedAuthority::getAuthority).collect(Collectors.joining(", ")));
        return super.view("home",modelAndView);
    }
}
