import { Moment } from 'moment';
import { IComment } from 'app/shared/model//comment.model';
import { ISignature } from 'app/shared/model//signature.model';
import { IDocument } from 'app/shared/model//document.model';
import { IBien } from 'app/shared/model//bien.model';

export interface IVisite {
    id?: number;
    label?: string;
    resume?: string;
    date?: Moment;
    comments?: IComment[];
    signatures?: ISignature[];
    supportsVisites?: IDocument[];
    bien?: IBien;
}

export class Visite implements IVisite {
    constructor(
        public id?: number,
        public label?: string,
        public resume?: string,
        public date?: Moment,
        public comments?: IComment[],
        public signatures?: ISignature[],
        public supportsVisites?: IDocument[],
        public bien?: IBien
    ) {}
}
