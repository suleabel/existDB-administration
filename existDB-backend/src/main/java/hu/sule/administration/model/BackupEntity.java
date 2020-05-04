package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BackupEntity {
    private String fileName;
    private String nrInSequence;
    private String date;
    private String incremental;
    private int previous;
}
