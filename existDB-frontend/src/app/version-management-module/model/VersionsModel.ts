import {ReversionsModel} from './ReversionsModel';

export class VersionsModel {
    doc: string;
    reversions: ReversionsModel;


    constructor(doc: string, reversions: ReversionsModel) {
        this.doc = doc;
        this.reversions = reversions;
    }
}
