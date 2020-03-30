package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class BackupEntity {
    private String fileName;
    private String nrInSequence;
    private String date;
    private String incremental;
    private String previous;
    private boolean downloadable;
    private String downloadLink;
}
