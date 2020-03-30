package hu.sule.administration.model;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class ForStoreResourceAndColl {
    private String url;
    private String fileName;
    private String content;
    private String mime;
    private boolean isBinary;

    public ForStoreResourceAndColl(String url, String fileName) {
        this.url = url;
        this.fileName = fileName;
    }
}
