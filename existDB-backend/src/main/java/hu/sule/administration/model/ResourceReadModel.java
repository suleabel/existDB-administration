package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ResourceReadModel {
    private String content;
    private boolean isBinary;
}
