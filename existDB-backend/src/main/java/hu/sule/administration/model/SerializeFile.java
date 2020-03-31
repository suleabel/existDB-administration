package hu.sule.administration.model;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class SerializeFile {
    private String content;
    private String path;
    private String name;
    private String parameters;
    private String isXml;
}
