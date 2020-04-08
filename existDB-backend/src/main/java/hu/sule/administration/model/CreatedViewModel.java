package hu.sule.administration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
public class CreatedViewModel {
    private String user;
    private String date;
    private String configLocation;
    private String queryName;
    private String viewLocation;
}
