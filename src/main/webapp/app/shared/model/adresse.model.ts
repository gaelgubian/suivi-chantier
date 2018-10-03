import { IContact } from 'app/shared/model//contact.model';
import { IBien } from 'app/shared/model//bien.model';

export interface IAdresse {
    id?: number;
    appartement?: string;
    entree?: string;
    numero?: string;
    voie?: string;
    complement?: string;
    codepostal?: number;
    ville?: string;
    latitude?: string;
    longitude?: string;
    contact?: IContact;
    bien?: IBien;
}

export class Adresse implements IAdresse {
    constructor(
        public id?: number,
        public appartement?: string,
        public entree?: string,
        public numero?: string,
        public voie?: string,
        public complement?: string,
        public codepostal?: number,
        public ville?: string,
        public latitude?: string,
        public longitude?: string,
        public contact?: IContact,
        public bien?: IBien
    ) {}
}
