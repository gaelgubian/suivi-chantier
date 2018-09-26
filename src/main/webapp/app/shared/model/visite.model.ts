import { Moment } from 'moment';
import { IBien } from 'app/shared/model//bien.model';
import { IDocument } from 'app/shared/model//document.model';
import { IComment } from 'app/shared/model//comment.model';
import { ISignature } from 'app/shared/model//signature.model';

export interface IVisite {
    id?: number;
    label?: string;
    date?: Moment;
    bien?: IBien;
    document?: IDocument;
    comments?: IComment[];
    signatures?: ISignature[];
}

export class Visite implements IVisite {
    constructor(
        public id?: number,
        public label?: string,
        public date?: Moment,
        public bien?: IBien,
        public document?: IDocument,
        public comments?: IComment[],
        public signatures?: ISignature[]
    ) {}
}
