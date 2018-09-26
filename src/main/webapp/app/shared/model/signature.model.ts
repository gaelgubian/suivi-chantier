import { IChantierIntervenant } from 'app/shared/model//chantier-intervenant.model';
import { IDocument } from 'app/shared/model//document.model';
import { IVisite } from 'app/shared/model//visite.model';

export interface ISignature {
    id?: number;
    imageContentType?: string;
    image?: any;
    chantierIntervenant?: IChantierIntervenant;
    document?: IDocument;
    visite?: IVisite;
}

export class Signature implements ISignature {
    constructor(
        public id?: number,
        public imageContentType?: string,
        public image?: any,
        public chantierIntervenant?: IChantierIntervenant,
        public document?: IDocument,
        public visite?: IVisite
    ) {}
}
