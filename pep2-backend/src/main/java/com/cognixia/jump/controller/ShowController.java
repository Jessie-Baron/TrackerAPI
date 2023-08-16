package com.cognixia.jump.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cognixia.jump.model.Show;
import com.cognixia.jump.model.User;
import com.cognixia.jump.service.ShowService;

@RequestMapping("/api")
@RestController
public class ShowController {
    
    @Autowired
    private ShowService service;

    @GetMapping("/shows")
    public List<Show> getAllShows() {
        return service.getShows();
    }

    @GetMapping("/shows/{id}")
    public ResponseEntity<Show> getShowById(@PathVariable String id) {
        Optional<Show> foundShow = service.getShowById(id);
        if (foundShow.isPresent()) {
            return ResponseEntity.status(200).body(foundShow.get());
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/shows/{userId}")
    public ResponseEntity<Show> createShow(@RequestBody Show show, @PathVariable String userId) throws Exception {
        Show createdShow = service.createShow(show, userId);
        return ResponseEntity.status(201).body(createdShow);
    }

    @PutMapping("/shows/{userId}/{id}")
    public ResponseEntity<Show> updateShow(@RequestBody Show show, @PathVariable String id, @PathVariable String userId) throws Exception {
        Show updatedShow = service.updateShow(show, userId);
        if (updatedShow == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(200).body(updatedShow);
        }
    }

    @DeleteMapping("/shows/{userId}/{id}")
    public ResponseEntity<Show> deleteShow(@PathVariable String id, @PathVariable String userId) throws Exception {
        Show deletedShow = service.deleteShowById(id, userId);
        if (deletedShow == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(200).body(deletedShow);
        }
    }
}
