package com.example.BoardAPITask.ResponseObject;

import lombok.Getter;
import lombok.Setter;
import java.util.List;
import java.util.Map;
@Getter
@Setter
public class BoardResponseEntity {
    private List<BoardInfo> boards;
    @Getter
    @Setter
    public static class BoardInfo {
        private Long board_id;
        private String name;
        private Map<Integer, String> columns;
    }
}

