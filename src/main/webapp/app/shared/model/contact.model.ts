import { IAdresse } from 'app/shared/model//adresse.model';
import { IChantierIntervenant } from 'app/shared/model//chantier-intervenant.model';

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

export interface IContact {
    id?: number;
    label?: string;
    login?: string;
    prenom?: string;
    nom?: string;
    email?: string;
    telephoneMobile?: string;
    telephone2?: string;
    fax?: string;
    description?: string;
    poste?: string;
    corpmetier?: Corpsmetier;
    adressecontact?: IAdresse;
    chantierIntervenant?: IChantierIntervenant;
}

export class Contact implements IContact {
    constructor(
        public id?: number,
        public label?: string,
        public login?: string,
        public prenom?: string,
        public nom?: string,
        public email?: string,
        public telephoneMobile?: string,
        public telephone2?: string,
        public fax?: string,
        public description?: string,
        public poste?: string,
        public corpmetier?: Corpsmetier,
        public adressecontact?: IAdresse,
        public chantierIntervenant?: IChantierIntervenant
    ) {}
}
