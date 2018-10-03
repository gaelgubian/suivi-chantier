import { IDocument } from 'app/shared/model//document.model';

export interface IDocumentTuile {
    id?: number;
    contentContentType?: string;
    content?: any;
    documents?: IDocument;
}

export class DocumentTuile implements IDocumentTuile {
    constructor(public id?: number, public contentContentType?: string, public content?: any, public documents?: IDocument) {}
}
