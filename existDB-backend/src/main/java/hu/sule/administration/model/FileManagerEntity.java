package hu.sule.administration.model;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
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
