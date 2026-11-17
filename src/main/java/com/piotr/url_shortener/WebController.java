package com.piotr.url_shortener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Controller
public class WebController {
    private final LinkServiceImpl service;

    public WebController(LinkServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public String index(Model model) {
        List<Link> list = service.getAllLink();
        Collections.reverse(list);
        Link link = new Link();
        model.addAttribute("link", link);
        model.addAttribute("allLinksList",list);
        return "index";
    }
}
