export interface IAdresse {
    id?: number;
    numero?: string;
    rue?: string;
    complement?: string;
    codepostal?: number;
    ville?: string;
    positionx?: string;
    positiony?: string;
}

export class Adresse implements IAdresse {
    constructor(
        public id?: number,
        public numero?: string,
        public rue?: string,
        public complement?: string,
        public codepostal?: number,
        public ville?: string,
        public positionx?: string,
        public positiony?: string
    ) {}
}
