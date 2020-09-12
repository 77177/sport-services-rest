package org.application.models.custom;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RequestRecord {

    public RequestRecord(String type, String out, String to, LocalDate date){
        this.type = type;
        this.out = out;
        this.to = to;
        this.date = date;
    }

    private Long id;
    private String type;
    private String out;
    private String to;
    private LocalDate date;
}
