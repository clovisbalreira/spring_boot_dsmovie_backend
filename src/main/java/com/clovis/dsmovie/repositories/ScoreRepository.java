package com.clovis.dsmovie.repositories;

import com.clovis.dsmovie.entities.Score;
import com.clovis.dsmovie.entities.ScorePK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, ScorePK> {
}
