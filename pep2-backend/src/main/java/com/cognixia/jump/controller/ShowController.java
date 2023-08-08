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

    @PostMapping("/shows")
    public ResponseEntity<Show> createShow(@RequestBody Show show) {
        Show createdShow = service.createShow(show);
        return ResponseEntity.status(201).body(createdShow);
    }

    @PutMapping("/shows/{id}")
    public ResponseEntity<Show> updateShow(@RequestBody Show show, @PathVariable String id) {
        Show updatedShow = service.updateShow(show);
        if (updatedShow == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(200).body(updatedShow);
        }
    }

    @DeleteMapping("/shows/{id}")
    public ResponseEntity<Show> deleteShow(@PathVariable String id) {
        Show deletedShow = service.deleteShowById(id);
        if (deletedShow == null) {
            return ResponseEntity.status(404).build();
        } else {
            return ResponseEntity.status(200).body(deletedShow);
        }
    }
}
