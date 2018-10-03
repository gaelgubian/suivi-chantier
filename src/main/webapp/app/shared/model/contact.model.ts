import { IAdresse } from 'app/shared/model//adresse.model';

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
    raisonsociale?: string;
    prenom?: string;
    nom?: string;
    email?: string;
    telephoneMobile?: string;
    telephone2?: string;
    fax?: string;
    description?: string;
    poste?: string;
    corpmetier?: Corpsmetier;
    adresse?: IAdresse;
}

export class Contact implements IContact {
    constructor(
        public id?: number,
        public label?: string,
        public login?: string,
        public raisonsociale?: string,
        public prenom?: string,
        public nom?: string,
        public email?: string,
        public telephoneMobile?: string,
        public telephone2?: string,
        public fax?: string,
        public description?: string,
        public poste?: string,
        public corpmetier?: Corpsmetier,
        public adresse?: IAdresse
    ) {}
}
