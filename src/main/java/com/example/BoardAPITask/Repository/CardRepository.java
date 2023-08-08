package com.example.BoardAPITask.Repository;

import com.example.BoardAPITask.Models.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface CardRepository extends JpaRepository<CardModel, Long> {
    List<CardModel> findAllByBoardId(Long boardId);

    Optional<CardModel> findByIdAndBoardId(Long cardId, Long boardId);

}

