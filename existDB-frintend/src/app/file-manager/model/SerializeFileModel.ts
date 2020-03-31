export class SerializeFileModel {
    content: string;
    path: string;
    name: string;
    parameter: string;
    isXml: string;

    constructor(content: string, path: string, name: string, parameter: string, isXml: string) {
        this.content = content;
        this.path = path;
        this.name = name;
        this.parameter = parameter;
        this.isXml = isXml;
    }
}
