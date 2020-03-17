package com.chat.chatapp.controller;

import com.chat.chatapp.model.Person;
import com.chat.chatapp.model.PersonService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Set;

@Controller
public class ChatController {
    @Qualifier("personService")
    private final PersonService personService;

    @Autowired
    public ChatController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/login")
    public String home(Model m, @RequestParam("username") String username, @RequestParam("password") String password, HttpServletRequest request) {
        HttpSession session = request.getSession();
        Person p = personService.getAuthenticatedUser(username, password);
        if (p != null) {
            p.setStatus("Online");
        }
        session.setAttribute("user", personService.getAuthenticatedUser(username, password));


        Person pp = (Person) request.getSession().getAttribute("user");
        System.out.println(pp.getFirstName());

        return "redirect:/";
    }

    @GetMapping("/")
    public String home(Model model) {

        return "index";
    }

    @PostMapping("/addfriend/{name}")
    public void addFriend(HttpServletRequest request, HttpServletResponse response, @PathVariable("name") String name) {
        HttpSession session = request.getSession();
        Person person = (Person) session.getAttribute("user");
        if (person != null) {

            Person friend = personService.getPerson(name + "@ucll.be");
            System.out.println(friend.getFirstName());
            person.addFriend(friend);
        }
    }

    @GetMapping("/friends")
    public void getFriendsList(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();
        Person p = (Person) session.getAttribute("user");

        if (p != null) {
            response.setContentType("application/json");
            try {

                System.out.println(toJson(p.getVrienden()));
                response.getWriter().write(String.valueOf(toJson(p.getVrienden())));
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    @PostMapping("/change/{status}")
    public void setStatus(HttpServletResponse response, HttpServletRequest request, @PathVariable("status") String status) {
        HttpSession session = request.getSession();
        try {
            Person person = (Person) session.getAttribute("user");
            person.setStatus(status);
            response.getWriter().write(person.getFirstName() + " - " + person.getStatus());

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Object toJson(List<Person> list){
        JsonObject json = new JsonObject();
        for (Person u:list){
            JsonObject user = new JsonObject();
            user.addProperty("name",u.getFirstName());
            user.addProperty("statusname",u.getStatus());
            json.add(u.getFirstName(),user);
        }
        return json.toString();
    }

}
