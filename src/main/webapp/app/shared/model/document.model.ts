import { ISignature } from 'app/shared/model//signature.model';
import { IDocumentTuile } from 'app/shared/model//document-tuile.model';
import { IVisite } from 'app/shared/model//visite.model';
import { IChantier } from 'app/shared/model//chantier.model';
import { IBien } from 'app/shared/model//bien.model';

export interface IDocument {
    id?: number;
    label?: string;
    description?: string;
    path?: string;
    signatures?: ISignature[];
    documentTuiles?: IDocumentTuile[];
    visite?: IVisite;
    chantier?: IChantier;
    chantier?: IBien;
}

export class Document implements IDocument {
    constructor(
        public id?: number,
        public label?: string,
        public description?: string,
        public path?: string,
        public signatures?: ISignature[],
        public documentTuiles?: IDocumentTuile[],
        public visite?: IVisite,
        public chantier?: IChantier,
        public chantier?: IBien
    ) {}
}
