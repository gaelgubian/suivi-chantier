import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChantier } from 'app/shared/model/chantier.model';

type EntityResponseType = HttpResponse<IChantier>;
type EntityArrayResponseType = HttpResponse<IChantier[]>;

@Injectable({ providedIn: 'root' })
export class ChantierService {
    private resourceUrl = SERVER_API_URL + 'api/chantiers';

    constructor(private http: HttpClient) {}

    create(chantier: IChantier): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(chantier);
        return this.http
            .post<IChantier>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(chantier: IChantier): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(chantier);
        return this.http
            .put<IChantier>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IChantier>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IChantier[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    private convertDateFromClient(chantier: IChantier): IChantier {
        const copy: IChantier = Object.assign({}, chantier, {
            dateDebut: chantier.dateDebut != null && chantier.dateDebut.isValid() ? chantier.dateDebut.format(DATE_FORMAT) : null,
            dateFin: chantier.dateFin != null && chantier.dateFin.isValid() ? chantier.dateFin.format(DATE_FORMAT) : null
        });
        return copy;
    }

    private convertDateFromServer(res: EntityResponseType): EntityResponseType {
        res.body.dateDebut = res.body.dateDebut != null ? moment(res.body.dateDebut) : null;
        res.body.dateFin = res.body.dateFin != null ? moment(res.body.dateFin) : null;
        return res;
    }

    private convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        res.body.forEach((chantier: IChantier) => {
            chantier.dateDebut = chantier.dateDebut != null ? moment(chantier.dateDebut) : null;
            chantier.dateFin = chantier.dateFin != null ? moment(chantier.dateFin) : null;
        });
        return res;
    }
}
