import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBien } from 'app/shared/model/bien.model';

type EntityResponseType = HttpResponse<IBien>;
type EntityArrayResponseType = HttpResponse<IBien[]>;

@Injectable({ providedIn: 'root' })
export class BienService {
    private resourceUrl = SERVER_API_URL + 'api/biens';

    constructor(private http: HttpClient) {}

    create(bien: IBien): Observable<EntityResponseType> {
        return this.http.post<IBien>(this.resourceUrl, bien, { observe: 'response' });
    }

    update(bien: IBien): Observable<EntityResponseType> {
        return this.http.put<IBien>(this.resourceUrl, bien, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBien>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBien[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
