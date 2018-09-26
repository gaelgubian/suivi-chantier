import { Moment } from 'moment';
import { IBien } from 'app/shared/model//bien.model';
import { IDocument } from 'app/shared/model//document.model';
import { IChantierIntervenant } from 'app/shared/model//chantier-intervenant.model';

export interface IChantier {
    id?: number;
    label?: string;
    imageContentType?: string;
    image?: any;
    description?: string;
    dateDebut?: Moment;
    dateFin?: Moment;
    biens?: IBien[];
    documents?: IDocument[];
    chantierIntervenants?: IChantierIntervenant[];
}

export class Chantier implements IChantier {
    constructor(
        public id?: number,
        public label?: string,
        public imageContentType?: string,
        public image?: any,
        public description?: string,
        public dateDebut?: Moment,
        public dateFin?: Moment,
        public biens?: IBien[],
        public documents?: IDocument[],
        public chantierIntervenants?: IChantierIntervenant[]
    ) {}
}
