import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDocumentTuile } from 'app/shared/model/document-tuile.model';

type EntityResponseType = HttpResponse<IDocumentTuile>;
type EntityArrayResponseType = HttpResponse<IDocumentTuile[]>;

@Injectable({ providedIn: 'root' })
export class DocumentTuileService {
    private resourceUrl = SERVER_API_URL + 'api/document-tuiles';

    constructor(private http: HttpClient) {}

    create(documentTuile: IDocumentTuile): Observable<EntityResponseType> {
        return this.http.post<IDocumentTuile>(this.resourceUrl, documentTuile, { observe: 'response' });
    }

    update(documentTuile: IDocumentTuile): Observable<EntityResponseType> {
        return this.http.put<IDocumentTuile>(this.resourceUrl, documentTuile, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDocumentTuile>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDocumentTuile[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }
}
