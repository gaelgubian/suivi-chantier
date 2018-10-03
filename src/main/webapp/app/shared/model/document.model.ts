import { ISignature } from 'app/shared/model//signature.model';
import { IDocumentTuile } from 'app/shared/model//document-tuile.model';
import { IComment } from 'app/shared/model//comment.model';
import { IChantier } from 'app/shared/model//chantier.model';
import { IBien } from 'app/shared/model//bien.model';
import { IVisite } from 'app/shared/model//visite.model';

export interface IDocument {
    id?: number;
    label?: string;
    description?: string;
    path?: string;
    filename?: string;
    contentContentType?: string;
    content?: any;
    signatures?: ISignature[];
    tuiles?: IDocumentTuile[];
    comments?: IComment[];
    chantier?: IChantier;
    bien?: IBien;
    visite?: IVisite;
}

export class Document implements IDocument {
    constructor(
        public id?: number,
        public label?: string,
        public description?: string,
        public path?: string,
        public filename?: string,
        public contentContentType?: string,
        public content?: any,
        public signatures?: ISignature[],
        public tuiles?: IDocumentTuile[],
        public comments?: IComment[],
        public chantier?: IChantier,
        public bien?: IBien,
        public visite?: IVisite
    ) {}
}
