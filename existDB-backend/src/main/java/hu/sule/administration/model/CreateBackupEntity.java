package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CreateBackupEntity {
    private boolean isZip;
    private boolean isIncremental;
}
