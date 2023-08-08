package com.example.BoardAPITask.ResponseObject;

import com.example.BoardAPITask.Models.CardModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CardResponseObject {
    private Long card_id;
    private String title;
    private String description;
    private Integer section; // Change the type to Integer

    public static List<CardResponseObject> fromCardList(List<CardModel> cards) {
        List<CardResponseObject> responseList = new ArrayList<>();
        for (CardModel card : cards) {
            CardResponseObject response = new CardResponseObject();
            response.setCard_id(card.getId());
            response.setTitle(card.getTitle());
            response.setDescription(card.getDescription());
            response.setSection(card.getSection());
            responseList.add(response);
        }
        return responseList;
    }
}
