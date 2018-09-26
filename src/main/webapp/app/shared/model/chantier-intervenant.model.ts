import { IContact } from 'app/shared/model//contact.model';
import { ISignature } from 'app/shared/model//signature.model';
import { IChantier } from 'app/shared/model//chantier.model';

export const enum Corpsmetier {
    TERRASSEMENT = 'TERRASSEMENT',
    GROS_OEUVRE = 'GROS_OEUVRE',
    MENUISERIE = 'MENUISERIE',
    ELECTRICITE = 'ELECTRICITE',
    PLOMBERIE = 'PLOMBERIE',
    CHAUFFAGE = 'CHAUFFAGE',
    CARRELAGE = 'CARRELAGE',
    PLATRE = 'PLATRE'
}

export const enum Role {
    MOA = 'MOA',
    MOE = 'MOE',
    ETUDE = 'ETUDE',
    CONTROLE = 'CONTROLE'
}

export interface IChantierIntervenant {
    id?: number;
    corpmetier?: Corpsmetier;
    role?: Role;
    contact?: IContact;
    signature?: ISignature;
    chantier?: IChantier;
}

export class ChantierIntervenant implements IChantierIntervenant {
    constructor(
        public id?: number,
        public corpmetier?: Corpsmetier,
        public role?: Role,
        public contact?: IContact,
        public signature?: ISignature,
        public chantier?: IChantier
    ) {}
}
