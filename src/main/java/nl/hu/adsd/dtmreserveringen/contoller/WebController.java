package nl.hu.adsd.dtmreserveringen.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebController {

    @RequestMapping("/")
    public String index() {
        return "html/index.html";
    }

    @RequestMapping("/product")
    public String product() {
        return "html/product-info.html";
    }

    @RequestMapping("/login")
    public String login() {
        return "html/login.html";
    }
    
    @RequestMapping("/register")
    public String register() {
        return "html/register.html";
    }

    @RequestMapping("/cart")
    public String cart() {
        return "html/cart.html";
    }

    @RequestMapping("/admin")
    public String admin() {
        return "html/admin.html";
    }

    @RequestMapping("/test")
    public String test() {
        return "html/calendar-test.html";
    }

    @RequestMapping("/e")
    public String error() {
        return "errors/error.html";
    }

    @RequestMapping("/error404")
    public String error404() {
        return "errors/error-404.html";
    }

    @RequestMapping("/error500")
    public String error500() {
        return "errors/error-500.html";
    }

    @RequestMapping("/favicon.ico")
    public String favIcon() {
        return "icons/favicon.ico";
    }
}