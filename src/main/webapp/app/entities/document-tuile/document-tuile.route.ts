import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { DocumentTuile } from 'app/shared/model/document-tuile.model';
import { DocumentTuileService } from './document-tuile.service';
import { DocumentTuileComponent } from './document-tuile.component';
import { DocumentTuileDetailComponent } from './document-tuile-detail.component';
import { DocumentTuileUpdateComponent } from './document-tuile-update.component';
import { DocumentTuileDeletePopupComponent } from './document-tuile-delete-dialog.component';
import { IDocumentTuile } from 'app/shared/model/document-tuile.model';

@Injectable({ providedIn: 'root' })
export class DocumentTuileResolve implements Resolve<IDocumentTuile> {
    constructor(private service: DocumentTuileService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((documentTuile: HttpResponse<DocumentTuile>) => documentTuile.body));
        }
        return of(new DocumentTuile());
    }
}

export const documentTuileRoute: Routes = [
    {
        path: 'document-tuile',
        component: DocumentTuileComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'DocumentTuiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-tuile/:id/view',
        component: DocumentTuileDetailComponent,
        resolve: {
            documentTuile: DocumentTuileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTuiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-tuile/new',
        component: DocumentTuileUpdateComponent,
        resolve: {
            documentTuile: DocumentTuileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTuiles'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'document-tuile/:id/edit',
        component: DocumentTuileUpdateComponent,
        resolve: {
            documentTuile: DocumentTuileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTuiles'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const documentTuilePopupRoute: Routes = [
    {
        path: 'document-tuile/:id/delete',
        component: DocumentTuileDeletePopupComponent,
        resolve: {
            documentTuile: DocumentTuileResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DocumentTuiles'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
