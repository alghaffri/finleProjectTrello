package com.example.BoardAPITask.Controller;

import com.example.BoardAPITask.Models.BoardModel;
import com.example.BoardAPITask.Models.CardModel;
import com.example.BoardAPITask.RequestObject.CardRequestEntity;
import com.example.BoardAPITask.ResponseObject.CardResponseObject;
import com.example.BoardAPITask.Service.BoardService;
import com.example.BoardAPITask.Service.CardService;
import org.hibernate.engine.internal.Collections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/boards")
public class CardController {
    @Autowired
    private CardService cardService;
    @Autowired
    private BoardService boardService;
    private CardResponseObject response;

    @PostMapping("/{board_id}/cards")
    public ResponseEntity<CardResponseObject> addCardToBoard(
            @PathVariable("board_id") Long boardId,
            @RequestBody CardRequestEntity cardRequest
    ) {
        try {
            CardModel newCard = cardService.createNewCard(boardId, cardRequest);
            CardResponseObject response = new CardResponseObject();
            response.setCard_id(newCard.getId());
            response.setTitle(newCard.getTitle());
            response.setDescription(newCard.getDescription());
            response.setSection(newCard.getSection());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Endpoint for getting all cards from a board
    @GetMapping(value = "/{boardId}/cards")
    public List<CardResponseObject> getAllCards(@PathVariable Long boardId) {
        BoardModel board = boardService.getBoardById(boardId);
        if (board == null) {
            // If the board with the given ID is not found, return an empty list
            return List.of();
        }

        List<CardModel> cards = cardService.getAllCards(board);
        return CardResponseObject.fromCardList(cards);
    }
    @GetMapping("/{board_id}/cards/{card_id}")
    public ResponseEntity<CardResponseObject> getCardFromBoard(
            @PathVariable("board_id") Long boardId,
            @PathVariable("card_id") Long cardId
    ) {
        CardModel card = cardService.getCardByIdAndBoardId(cardId, boardId);
        if (card == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        CardResponseObject response = new CardResponseObject();
        response.setCard_id(card.getId());
        response.setTitle(card.getTitle());
        response.setDescription(card.getDescription());
        response.setSection(card.getSection());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @PutMapping("/{board_id}/cards/{card_id}")
    public ResponseEntity<CardResponseObject> updateCardOnBoard(
            @PathVariable("board_id") Long boardId,
            @PathVariable("card_id") Long cardId,
            @RequestBody CardRequestEntity cardRequest
    ) {
        try {
            // First, check if the board with the given ID exists
            BoardModel board = boardService.getBoardById(boardId);
            if (board == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Next, check if the card with the given ID exists in the board's cards list
            CardModel existingCard = cardService.getCardByIdAndBoardId(cardId, boardId);
            if (existingCard == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            // Update the card properties
            existingCard.setTitle(cardRequest.getTitle());
            existingCard.setDescription(cardRequest.getDescription());
            existingCard.setSection(cardRequest.getSection());

            // Save the updated card
            CardModel updatedCard = cardService.updateCard(existingCard);

            // Create the response body
            CardResponseObject response = new CardResponseObject();
            response.setCard_id(updatedCard.getId());
            response.setTitle(updatedCard.getTitle());
            response.setDescription(updatedCard.getDescription());
            response.setSection(updatedCard.getSection());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/{board_id}/cards/{card_id}")
    public ResponseEntity<Map<String, String>> deleteCardFromBoard(
            @PathVariable("board_id") Long boardId,
            @PathVariable("card_id") Long cardId
    ) {
        boolean isDeleted = cardService.deleteCard(boardId, cardId);
        if (isDeleted) {
            String message = "Card with ID " + cardId + " has been deleted successfully from board " + boardId + ".";
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } else {
            String message = "Card with ID " + cardId + " does not exist in board " + boardId + ".";
            Map<String, String> response = new HashMap<>();
            response.put("message", message);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }

}

