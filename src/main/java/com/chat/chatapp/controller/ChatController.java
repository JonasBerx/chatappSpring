package com.chat.chatapp.controller;

import com.chat.chatapp.db.Comments;
import com.chat.chatapp.model.Comment;
import com.chat.chatapp.model.Person;
import com.chat.chatapp.model.PersonService;
import com.google.gson.JsonObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
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
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ServerEndpoint("/comment")
@ComponentScan
@Controller
public class ChatController {
    @Qualifier("personService")
    private final PersonService personService;

    private static final Set<HttpSession> sessions = Collections.synchronizedSet(new HashSet<HttpSession>());

    @Autowired
    public ChatController(PersonService personService) {
        this.personService = personService;
    }

    @PostMapping("/logout")
    public String logout(HttpServletRequest request) {
        request.getSession().invalidate();
        return "redirect:/";
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

    @PostMapping("/addcomment/{name}/{descr}/{rating}")
    public void getComments(HttpServletRequest request, HttpServletResponse response, @PathVariable("name") String name,@PathVariable("descr") String descr,@PathVariable("rating") int rating) {
        System.out.println("getting comments");
        response.setContentType("text");
        Comment comment = new Comment(name, descr, rating);
        System.out.println(comment.getName());
        try {
            response.getWriter().write(comment.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @GetMapping("/comments")
    public void getComments(HttpServletRequest request, HttpServletResponse response) {
        try {
            System.out.println("LOLOLOL");
            response.getWriter().write("Test");

        } catch (IOException e) {
            e.printStackTrace();

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
