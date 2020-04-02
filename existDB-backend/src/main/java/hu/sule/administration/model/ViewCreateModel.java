package hu.sule.administration.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ViewCreateModel {
    private String viewCollection;
    private String viewName;
    private String queryExpression;
}
