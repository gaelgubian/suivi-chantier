export interface IIcon {
    id?: number;
    label?: string;
    imageContentType?: string;
    image?: any;
}

export class Icon implements IIcon {
    constructor(public id?: number, public label?: string, public imageContentType?: string, public image?: any) {}
}
