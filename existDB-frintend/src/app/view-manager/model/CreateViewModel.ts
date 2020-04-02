export class CreateViewModel {
    viewCollection: string;
    viewName: string;
    queryExpression: string;

    constructor(viewCollection: string, viewName: string, queryExpression: string) {
        this.viewCollection = viewCollection;
        this.viewName = viewName;
        this.queryExpression = queryExpression;
    }
}
