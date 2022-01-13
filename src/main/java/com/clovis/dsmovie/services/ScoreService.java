package com.clovis.dsmovie.services;

import com.clovis.dsmovie.dto.MovieDTO;
import com.clovis.dsmovie.dto.ScoreDTO;
import com.clovis.dsmovie.entities.Movie;
import com.clovis.dsmovie.entities.Score;
import com.clovis.dsmovie.entities.User;
import com.clovis.dsmovie.repositories.MovieRepository;
import com.clovis.dsmovie.repositories.ScoreRepository;
import com.clovis.dsmovie.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class ScoreService {

    @Autowired
    private MovieRepository movierepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ScoreRepository scoreRepository;

    @Transactional
    public MovieDTO saveScore(ScoreDTO dto){
        User user = userRepository.findByEmail(dto.getEmail());
        if(user == null){
            user = new User();
            user.setEmail(dto.getEmail());
            user = userRepository.saveAndFlush(user);
        }

        Movie movie = movierepository.findById(dto.getMovieId()).get();

        Score score = new Score();
        score.setMovie(movie);
        score.setUser(user);
        score.setValue(dto.getScore());

        score = scoreRepository.saveAndFlush(score);

        double sum = 0.0;
        double med = 0;
        for (Score s : movie.getScores()){
            sum = sum + s.getValue();
            med = med + 1;
        }

        //double avg = sum / movie.getScore().size();
        double avg = sum / med;

        movie.setScore(avg);
        movie.setCount((int) med);

        movie = movierepository.save(movie);
        return new MovieDTO(movie);
    }

}
