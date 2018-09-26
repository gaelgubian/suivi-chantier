import { IChantier } from 'app/shared/model//chantier.model';
import { IAdresse } from 'app/shared/model//adresse.model';
import { IDocument } from 'app/shared/model//document.model';
import { IVisite } from 'app/shared/model//visite.model';

export interface IBien {
    id?: number;
    label?: string;
    description?: string;
    chantier?: IChantier;
    adressechantier?: IAdresse;
    documents?: IDocument[];
    visites?: IVisite[];
}

export class Bien implements IBien {
    constructor(
        public id?: number,
        public label?: string,
        public description?: string,
        public chantier?: IChantier,
        public adressechantier?: IAdresse,
        public documents?: IDocument[],
        public visites?: IVisite[]
    ) {}
}
