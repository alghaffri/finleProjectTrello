package com.example.BoardAPITask.Models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Setter
@Getter
public class BoardModel {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;
    String title;
    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<CardModel> cards = new ArrayList<>();
    public void removeCard(CardModel card) {
        cards.remove(card);

    }

}