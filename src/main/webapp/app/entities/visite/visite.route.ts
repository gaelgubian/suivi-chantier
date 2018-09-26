import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Visite } from 'app/shared/model/visite.model';
import { VisiteService } from './visite.service';
import { VisiteComponent } from './visite.component';
import { VisiteDetailComponent } from './visite-detail.component';
import { VisiteUpdateComponent } from './visite-update.component';
import { VisiteDeletePopupComponent } from './visite-delete-dialog.component';
import { IVisite } from 'app/shared/model/visite.model';

@Injectable({ providedIn: 'root' })
export class VisiteResolve implements Resolve<IVisite> {
    constructor(private service: VisiteService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((visite: HttpResponse<Visite>) => visite.body));
        }
        return of(new Visite());
    }
}

export const visiteRoute: Routes = [
    {
        path: 'visite',
        component: VisiteComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Visites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visite/:id/view',
        component: VisiteDetailComponent,
        resolve: {
            visite: VisiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visite/new',
        component: VisiteUpdateComponent,
        resolve: {
            visite: VisiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visites'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'visite/:id/edit',
        component: VisiteUpdateComponent,
        resolve: {
            visite: VisiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visites'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visitePopupRoute: Routes = [
    {
        path: 'visite/:id/delete',
        component: VisiteDeletePopupComponent,
        resolve: {
            visite: VisiteResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Visites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
