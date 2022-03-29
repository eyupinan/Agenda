package com.kartaca.challenge.controller;

import com.kartaca.challenge.Enums.Roles;
import com.kartaca.challenge.Model.User;
import com.kartaca.challenge.Service.AccountService;
import com.kartaca.challenge.dto.NewUserData;
import com.kartaca.challenge.dto.UserPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class SessionController {
    @Autowired
    private AccountService userService;
    @GetMapping("/")
    public ModelAndView home(Authentication auth,Model model, HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        model.addAttribute("sessionId", session.getId());
        System.out.println(auth);
        if (auth!=null){
            model.addAttribute("username",((UserPrincipal)auth.getPrincipal()).getUsername());
        }
        

        return new ModelAndView("deneme");
    }
    @GetMapping("/persistMessage")
    public ModelAndView sa(Authentication auth,Model model, HttpSession session){
        @SuppressWarnings("unchecked")
        List<String> messages = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");

        if (messages == null) {
            messages = new ArrayList<>();
        }
        model.addAttribute("sessionMessages", messages);
        model.addAttribute("sessionId", session.getId());
        if (auth!=null){
            model.addAttribute("username",((UserPrincipal)auth.getPrincipal()).getUsername());
        }
        

        return new ModelAndView("deneme");
    }
    @PostMapping(value="/persistMessage" ,consumes=MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public RedirectView  persistMessage(@RequestParam("msg") String msg,  HttpSession session) {
        @SuppressWarnings("unchecked")
        List<String> msgs = (List<String>) session.getAttribute("MY_SESSION_MESSAGES");
        if (msgs == null) {
            msgs = new ArrayList<>();
            session.setAttribute("MY_SESSION_MESSAGES", msgs);
        }
        msgs.add(msg);
        session.setAttribute("MY_SESSION_MESSAGES", msgs);
        return new RedirectView ("/persistMessage");
    }

    @PostMapping("/destroy")
    public String destroySession(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
    }
    @GetMapping("/login")
    public ModelAndView login(Model model,@RequestParam(required = false) String err) {
        model.addAttribute("login_path","/api/login");
        return new ModelAndView("login");
    }
    @GetMapping("/register")
    public ModelAndView register(Model model,@RequestParam(required = false) String err) {
        model.addAttribute("user_create_path","/api/user/create");
        return new ModelAndView("register");
    }
    @PostMapping(value="api/user/create",consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public RedirectView createUserJson(NewUserData data){
        data.setRole(Roles.ROLE_USER);
        try{
            userService.saveUser(data);
        }
        catch (Exception e){
            return  new RedirectView("/register?err=true");
        }

        return new RedirectView("/login");
    }
    @PostMapping(value="/sa")
    public void asdf(@RequestParam("sa") String sa){
        System.out.println("geldi:"+sa);
    }
    
}
