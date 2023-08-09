package com.cognixia.jump.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cognixia.jump.model.Show;
import com.cognixia.jump.repository.ShowRepository;

@Service
public class ShowService {

    @Autowired
    private ShowRepository repo;

    public List<Show> getShows() {
        return repo.findAll();
    }

    public Optional<Show> getShowById(String id) {
        return repo.findById(id);
    }

    public Show createShow(Show show) {
        show.setId(null);
        return repo.save(show);

    }

    public Show updateShow(Show show) {
        Optional<Show> foundShow = repo.findById(show.getId());
        if (foundShow.isEmpty()) return null;

        return repo.save(show);
    }

    public Show deleteShowById(String id) {
        Optional<Show> foundShow = repo.findById(id);
        if (foundShow.isEmpty()) return null;
        repo.deleteById(id);
        return foundShow.get();
    }
}
