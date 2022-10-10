package com.sparta.sakilaweb.repository;

import com.sparta.sakilaweb.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ActorRepository extends JpaRepository<Actor, Integer> {

    public Optional<Actor> findActorByFirstNameAndLastName(String firstName, String lastName);
}