import { Moment } from 'moment';
import { IIcon } from 'app/shared/model//icon.model';
import { IVisite } from 'app/shared/model//visite.model';
import { IDocument } from 'app/shared/model//document.model';

export const enum CommentType {
    ADMINISTATIF = 'ADMINISTATIF',
    PLANS = 'PLANS',
    ETUDE = 'ETUDE',
    AVANCEMENT = 'AVANCEMENT',
    SECURITE = 'SECURITE',
    NON_CONFORMITE = 'NON_CONFORMITE',
    DIVERS = 'DIVERS'
}

export const enum CommentState {
    A_VERIFIER = 'A_VERIFIER',
    A_CORRIGER = 'A_CORRIGER',
    CORRIGE = 'CORRIGE',
    VALIDE = 'VALIDE',
    ABANDONNE = 'ABANDONNE'
}

export interface IComment {
    id?: number;
    label?: string;
    comment?: string;
    positionx?: number;
    positiony?: number;
    width?: number;
    heigth?: number;
    modified?: Moment;
    echeance?: Moment;
    imageContentType?: string;
    image?: any;
    type?: CommentType;
    state?: CommentState;
    icon?: IIcon;
    visite?: IVisite;
    documents?: IDocument;
}

export class Comment implements IComment {
    constructor(
        public id?: number,
        public label?: string,
        public comment?: string,
        public positionx?: number,
        public positiony?: number,
        public width?: number,
        public heigth?: number,
        public modified?: Moment,
        public echeance?: Moment,
        public imageContentType?: string,
        public image?: any,
        public type?: CommentType,
        public state?: CommentState,
        public icon?: IIcon,
        public visite?: IVisite,
        public documents?: IDocument
    ) {}
}
