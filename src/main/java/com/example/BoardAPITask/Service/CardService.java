package com.example.BoardAPITask.Service;

import com.example.BoardAPITask.Models.BoardModel;
import com.example.BoardAPITask.Models.CardModel;
import com.example.BoardAPITask.Repository.BoardRepository;
import com.example.BoardAPITask.Repository.CardRepository;
import com.example.BoardAPITask.RequestObject.CardRequestEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CardService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private BoardRepository boardRepository;

    public CardModel createNewCard(Long boardId, CardRequestEntity cardRequest) {
        CardModel newCard = new CardModel();
        newCard.setTitle(cardRequest.getTitle());
        newCard.setDescription(cardRequest.getDescription());
        newCard.setSection(cardRequest.getSection());

        // Get the board from the database using the boardId
        BoardModel board = boardRepository.findById(boardId);
        if (board != null) {
            // Set the board for the new card
            newCard.setBoard(board);

            // Add the new card to the board's list of cards
            board.getCards().add(newCard);
        }

        return cardRepository.save(newCard);
    }

    public List<CardModel> getAllCardsFromBoard(Long boardId) {
        return cardRepository.findAllByBoardId(boardId);
    }

    public List<CardModel> getAllCards(BoardModel board) {
        return cardRepository.findAllByBoardId(board.getId());
    }

    public CardModel getCardByIdAndBoardId(Long cardId, Long boardId) {
        return cardRepository.findByIdAndBoardId(cardId, boardId).orElse(null);
    }

    public CardModel updateCard(CardModel card) {

        return cardRepository.save(card);
    }
    public boolean deleteCard(Long boardId, Long cardId) {
        // Check if the card exists in the specified board
        Optional<CardModel> optionalCard = cardRepository.findByIdAndBoardId(cardId, boardId);
        if (optionalCard.isPresent()) {
            CardModel cardToDelete = optionalCard.get();
            BoardModel board = cardToDelete.getBoard();
            if (board != null) {
                // Remove the card from the board and update the database
                board.removeCard(cardToDelete);
                cardRepository.delete(cardToDelete);
                return true;
            }
        }
        return false;
    }
}