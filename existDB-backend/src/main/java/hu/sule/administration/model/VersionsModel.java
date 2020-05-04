package hu.sule.administration.model;

import lombok.*;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class VersionsModel {
    private String doc;
    private ArrayList<ReversionsModel> reversions;
}
