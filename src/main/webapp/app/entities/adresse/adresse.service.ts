import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAdresse } from 'app/shared/model/adresse.model';

type EntityResponseType = HttpResponse<IAdresse>;
type EntityArrayResponseType = HttpResponse<IAdresse[]>;

@Injectable({ providedIn: 'root' })
export class AdresseService {
    private resourceUrl = SERVER_API_URL + 'api/adresses';

    constructor(private http: HttpClient) {}

    create(adresse: IAdresse): Observable<EntityResponseType> {
        return this.http.post<IAdresse>(this.resourceUrl, adresse, { observe: 'response' });
    }

    update(adresse: IAdresse): Observable<EntityResponseType> {
        return this.http.put<IAdresse>(this.resourceUrl, adresse, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAdresse>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAdresse[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
