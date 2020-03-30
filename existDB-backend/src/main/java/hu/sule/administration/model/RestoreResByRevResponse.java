package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class RestoreResByRevResponse {
    private String oldContent;
    private String newContent;
}
