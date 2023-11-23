package sg.edu.nus.iss.day13work.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/home")
public class HomeController {
    
    @GetMapping("/pagea")
    public String PageA(Model model, HttpSession session) {

        if (session.getAttribute("myFullName") != null) {
            model.addAttribute("SessionData", session.getAttribute("myFullName").toString());
            
        } else {
            model.addAttribute("SessionData", "There is no session object now.");
        }
        
        return "pagea";
    }

    @PostMapping("/pagea")
    public String PageAPost(@RequestBody MultiValueMap<String,String> form, Model model, HttpSession session) {

        String myFullName = form.getFirst("fullname");
        System.out.println("My fullname is " + myFullName);

        session.setAttribute("myFullName", myFullName);

        model.addAttribute("myName", session.getAttribute("myFullName").toString());
        return "pageb";
    }

    @GetMapping("/pageb")
    public String PageB(Model model, HttpSession session) {

        String myFullName = session.getAttribute("myFullName").toString();

        model.addAttribute("myName", myFullName);

        return "pagec";
    }

    @PostMapping("/pagec")
    public String PageCPost(Model model, HttpSession session) {
        session.invalidate();
        return "redirect:/home/pagea";
    }
}
