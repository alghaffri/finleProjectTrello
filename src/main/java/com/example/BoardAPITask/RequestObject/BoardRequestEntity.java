package com.example.BoardAPITask.RequestObject;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class BoardRequestEntity {
    private String name;
    private String title;
    private List<String> columns;
}
