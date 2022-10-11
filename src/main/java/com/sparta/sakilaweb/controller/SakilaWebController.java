package com.sparta.sakilaweb.controller;

import com.sparta.sakilaweb.dao.ActorDao;
import com.sparta.sakilaweb.dto.ActorDto;
import com.sparta.sakilaweb.entity.Actor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class SakilaWebController {

    private final ActorDao actorDao;

    public SakilaWebController(ActorDao actorDao) {
        this.actorDao = actorDao;
    }

    @GetMapping("/actor")
    public String testName(Model model) {
        model.addAttribute("newActor",new Actor());
        return "homepage";
    }

    @GetMapping("/actor/byid")
    public String findActor(@RequestParam int id, Model model) {
        ActorDto foundActor = actorDao.findById(id);
        model.addAttribute("foundActor",foundActor);
        return "displayActor";
    }

    @GetMapping("/actor/all")
    public String findAllActors(Model model) {
        List<ActorDto> allActors = actorDao.findAll();
        model.addAttribute("allActors",allActors);
        return "allActors";
    }

    @PostMapping("/actor/new")
    public String newActor(@ModelAttribute Actor actor, Model model) {
        ActorDto savedActor = actorDao.createNewActor(actor);
        if (savedActor.getId() != -1) {
            model.addAttribute("newActor",savedActor);
            return "result";
        }
        else {
            return "fail";
        }
    }

    @PatchMapping("/actor/update")
    public String updateActor(@ModelAttribute Actor actor, Model model) {
        ActorDto updatedActor = actorDao.updateActor(actor);
        model.addAttribute("updatedActor",updatedActor);
        return "updatedActor";
    }

    @DeleteMapping("/actor/delete/{id}")
    public String deleteActor(@PathVariable int id, Model model) {
        ActorDto foundActor = actorDao.deleteActor(id);
        if (foundActor.getId() != -1) {
            return "deleted";
        }
        else {
            return "fail";
        }
    }
}
