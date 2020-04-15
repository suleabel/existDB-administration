import {ReversionsModel} from '../app/version-management-module/model/ReversionsModel';

export class VersionsModel {
    doc: string;
    reversions: ReversionsModel;


    constructor(doc: string, reversions: ReversionsModel) {
        this.doc = doc;
        this.reversions = reversions;
    }
}
