package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateBackupEntity {
    private String saveLocation;
    private String isZip;
    private String isIncremental;
}
