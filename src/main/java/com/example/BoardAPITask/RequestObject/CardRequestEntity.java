package com.example.BoardAPITask.RequestObject;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardRequestEntity {
    private String title;
    private String description;
    private Integer section;
}
