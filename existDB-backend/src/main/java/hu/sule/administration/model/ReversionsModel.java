package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ReversionsModel {
    private String revNo;
    private String date;
    private String user;
}
