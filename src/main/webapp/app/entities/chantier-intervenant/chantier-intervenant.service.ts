import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';

type EntityResponseType = HttpResponse<IChantierIntervenant>;
type EntityArrayResponseType = HttpResponse<IChantierIntervenant[]>;

@Injectable({ providedIn: 'root' })
export class ChantierIntervenantService {
    private resourceUrl = SERVER_API_URL + 'api/chantier-intervenants';

    constructor(private http: HttpClient) {}

    create(chantierIntervenant: IChantierIntervenant): Observable<EntityResponseType> {
        return this.http.post<IChantierIntervenant>(this.resourceUrl, chantierIntervenant, { observe: 'response' });
    }

    update(chantierIntervenant: IChantierIntervenant): Observable<EntityResponseType> {
        return this.http.put<IChantierIntervenant>(this.resourceUrl, chantierIntervenant, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IChantierIntervenant>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IChantierIntervenant[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
