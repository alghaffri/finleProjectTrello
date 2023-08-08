package com.example.BoardAPITask.Service;

import com.example.BoardAPITask.Models.BoardModel;
import com.example.BoardAPITask.Repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class BoardService {
    @Autowired
    BoardRepository boardRepository;
    public BoardModel createNewBoard(BoardModel newbBoard){
        return boardRepository.save(newbBoard);
    }
    public List<BoardModel> getAllBoards(){
        return boardRepository.findAll();
    }
    public boolean deleteBoard(Integer boardId) {
        try {
            boardRepository.deleteById(boardId);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public BoardModel updateBoard(BoardModel board) {
        return boardRepository.save(board);
    }


    public BoardModel getBoardById(Long boardId) {
        return boardRepository.findById(boardId);
    }

}
