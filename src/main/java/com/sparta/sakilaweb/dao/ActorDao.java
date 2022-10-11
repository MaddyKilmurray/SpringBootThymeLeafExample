package com.sparta.sakilaweb.dao;

import com.sparta.sakilaweb.dto.ActorDto;
import com.sparta.sakilaweb.entity.Actor;
import com.sparta.sakilaweb.repository.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ActorDao {

    @Autowired
    private ActorRepository repository;

    public ActorDto findById(int id){
        Optional<Actor> result = repository.findById(id);
        if (result.isPresent()) {
            return convertToDTO(result.get());
        }
        return nullResponse();
    }

    public List<ActorDto> findAll() {
        List<Actor> allActors = repository.findAll();
        List<ActorDto> allActorDto = new ArrayList<>();
        for (int i = 0; i < allActors.size(); i++) {
            allActorDto.add(convertToDTO(allActors.get(i)));
        }
        return allActorDto;
    }

    public ActorDto createNewActor(Actor actor) {
        actor.setLastUpdate(Instant.now());
        repository.save(actor);
        Optional<Actor> savedActor = repository.findActorByFirstNameAndLastName(actor.getFirstName(),actor.getLastName());
        if (savedActor.isPresent()) {
            return convertToDTO(savedActor.get());
        }
        else {
            return nullResponse();
        }
    }

    public ActorDto updateActor(Actor actor) {
        Actor foundActor = repository.findById(actor.getId()).get();
        foundActor.setFirstName(actor.getFirstName());
        foundActor.setLastName(actor.getLastName());
        foundActor.setLastUpdate(Instant.now());
        repository.save(foundActor);
        Actor updatedActor = repository.findById(foundActor.getId()).get();
        return convertToDTO(updatedActor);
    }

    public ActorDto deleteActor(int id) {
        Optional<Actor> foundActor = repository.findById(id);
        if (foundActor.isPresent()) {
            try {
                repository.delete(foundActor.get());
                return convertToDTO(foundActor.get());
            }
            catch (Exception e) {
                return nullResponse();
            }
        }
        return nullResponse();
    }

    private ActorDto convertToDTO(Actor actor) {
        return new ActorDto(actor.getId(), actor.getFirstName(), actor.getLastName(), Instant.now());
    }

    private ActorDto nullResponse() {
        return new ActorDto(-1, null,null,null);
    }
}
