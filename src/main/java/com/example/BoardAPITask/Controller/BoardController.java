package com.example.BoardAPITask.Controller;

import com.example.BoardAPITask.Models.BoardModel;
import com.example.BoardAPITask.RequestObject.BoardRequestEntity;
import com.example.BoardAPITask.ResponseObject.BoardResponseEntity;
import com.example.BoardAPITask.Service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    BoardService boardService;

    @PostMapping
    public ResponseEntity<BoardResponseEntity.BoardInfo> postNewBoard(@RequestBody BoardRequestEntity boardRequest) {
        try {
            BoardModel newBoard = new BoardModel();
            newBoard.setTitle(boardRequest.getTitle());

            BoardModel createdBoard = boardService.createNewBoard(newBoard);

            BoardResponseEntity.BoardInfo responseInfo = new BoardResponseEntity.BoardInfo();
            responseInfo.setBoard_id(createdBoard.getId());
            responseInfo.setName(createdBoard.getTitle());

            // For simplicity, let's assume the columns are fixed as described in the example response
            Map<Integer, String> columns = new HashMap<>();
            columns.put(1, "To do");
            columns.put(2, "In progress");
            columns.put(3, "Done");
            responseInfo.setColumns(columns);

            return new ResponseEntity<>(responseInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @GetMapping
    public BoardResponseEntity getAllBoards() {
        List<BoardModel> boardModels = boardService.getAllBoards();
        BoardResponseEntity responseEntity = new BoardResponseEntity();
        List<BoardResponseEntity.BoardInfo> boards = new ArrayList<>();

        for (BoardModel boardModel : boardModels) {
            BoardResponseEntity.BoardInfo boardInfo = new BoardResponseEntity.BoardInfo();
            boardInfo.setBoard_id(boardModel.getId());
            boardInfo.setName(boardModel.getTitle());
            // For simplicity, let's assume the columns are fixed as described in the example response
            Map<Integer, String> columns = new HashMap<>();
            columns.put(1, "To do");
            columns.put(2, "In progress");
            columns.put(3, "Done");
            boardInfo.setColumns(columns);
            boards.add(boardInfo);
        }
        responseEntity.setBoards(boards);
        return responseEntity;
    }

    @DeleteMapping(value = "/{board_id}")
    public ResponseEntity<Map<String, Object>> deleteBoard(@PathVariable("board_id") Integer boardId) {
        Map<String, Object> response = new HashMap<>();
        try {
            boolean isDeleted = boardService.deleteBoard(boardId);
            if (isDeleted) {
                response.put("successful", true);
                response.put("message", "Board with ID " + boardId + " has been deleted successfully.");
                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.put("successful", false);
                response.put("message", "Board with ID " + boardId + " does not exist.");
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.put("successful", false);
            response.put("message", "Failed to delete board with ID " + boardId + ". Error: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @PutMapping("/{board_id}")
    public ResponseEntity<BoardResponseEntity.BoardInfo> updateBoard(@PathVariable("board_id") Long boardId, @RequestBody BoardRequestEntity boardRequest) {
        try {
            BoardModel existingBoard = boardService.getBoardById(boardId);
            if (existingBoard == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update the board properties
            existingBoard.setTitle(boardRequest.getName());

            BoardModel updatedBoard = boardService.updateBoard(existingBoard);

            BoardResponseEntity.BoardInfo responseInfo = new BoardResponseEntity.BoardInfo();
            responseInfo.setBoard_id(updatedBoard.getId());
            responseInfo.setName(updatedBoard.getTitle());

            // For simplicity, let's assume the columns are fixed as described in the example response
            Map<Integer, String> columns = new HashMap<>();
            columns.put(1, "To do");
            columns.put(2, "In progress");
            columns.put(3, "Done");
            responseInfo.setColumns(columns);

            return new ResponseEntity<>(responseInfo, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



}