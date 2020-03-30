package hu.sule.administration.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class FileManagerEntity {
    private boolean isFile;
    private String name;
    private String size;
    private String humanSize;
    private String modified;
    private boolean hidden;
    private boolean canRead;
    private boolean canWrite;
}
