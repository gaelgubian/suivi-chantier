import { IAdresse } from 'app/shared/model//adresse.model';
import { IDocument } from 'app/shared/model//document.model';
import { IVisite } from 'app/shared/model//visite.model';
import { IChantier } from 'app/shared/model//chantier.model';

export interface IBien {
    id?: number;
    label?: string;
    description?: string;
    adresseBien?: IAdresse;
    documents?: IDocument[];
    visites?: IVisite[];
    chantier?: IChantier;
}

export class Bien implements IBien {
    constructor(
        public id?: number,
        public label?: string,
        public description?: string,
        public adresseBien?: IAdresse,
        public documents?: IDocument[],
        public visites?: IVisite[],
        public chantier?: IChantier
    ) {}
}
