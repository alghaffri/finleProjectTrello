package com.example.BoardAPITask.Repository;

import com.example.BoardAPITask.Models.BoardModel;
import com.example.BoardAPITask.Models.CardModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardRepository extends JpaRepository<BoardModel,Integer> {


    BoardModel findById(Long boardId);
}
