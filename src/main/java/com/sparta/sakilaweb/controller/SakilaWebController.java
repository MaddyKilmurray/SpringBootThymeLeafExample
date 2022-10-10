package com.sparta.sakilaweb.controller;

import com.sparta.sakilaweb.entity.Actor;
import com.sparta.sakilaweb.repository.ActorRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Controller
public class SakilaWebController {

    private final ActorRepository repository;

    public SakilaWebController(ActorRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/actor")
    public String testName(Model model) {
        model.addAttribute("newActor",new Actor());
        return "homepage";
    }

    @GetMapping("/actor/byid")
    public String findActor(@RequestParam int id, Model model) {
        Actor result = repository.findById(id).get();
        model.addAttribute("foundActor",result);
        return "displayActor";
    }

    @GetMapping("/actor/all")
    public String findAllActors(Model model) {
        List<Actor> allActors = repository.findAll();
        model.addAttribute("allActors",allActors);
        return "allActors";
    }

    @PostMapping("/actor")
    public String newActor(@ModelAttribute Actor actor, Model model) {
        actor.setLastUpdate(Instant.now());
        repository.save(actor);
        Optional<Actor> savedActor = repository.findActorByFirstNameAndLastName(actor.getFirstName(),actor.getLastName());
        if (savedActor.isPresent()) {
            model.addAttribute("newActor",actor);
            return "result";
        }
        else {
            return "fail";
        }
    }

    @DeleteMapping("/actor/delete/{id}")
    public String deleteActor(@PathVariable int id, Model model) {
        Optional<Actor> foundActor = repository.findById(id);
        if (foundActor.isPresent()) {
            repository.delete(foundActor.get());
            model.addAttribute("deletedActor",foundActor.get());
            return "deleted";
        }
        else {
            return "fail";
        }
    }
}
