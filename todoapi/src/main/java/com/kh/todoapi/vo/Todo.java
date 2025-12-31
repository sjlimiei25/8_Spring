package com.kh.todoapi.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Date;

@NoArgsConstructor
@Getter
@Setter
public class Todo {
    private Long id;
    private String title;
    private String status;
    private Date createdAt;

    @Override
    public String toString() {
        return String.format("%s, %s, %s, %s", this.id, this.title, this.status, this.createdAt);
    }
}
