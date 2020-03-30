package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ReversionsModel {
    private String revNo;
    private String date;
    private String user;
}
