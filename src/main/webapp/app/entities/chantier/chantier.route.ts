import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Chantier } from 'app/shared/model/chantier.model';
import { ChantierService } from './chantier.service';
import { ChantierComponent } from './chantier.component';
import { ChantierDetailComponent } from './chantier-detail.component';
import { ChantierUpdateComponent } from './chantier-update.component';
import { ChantierDeletePopupComponent } from './chantier-delete-dialog.component';
import { IChantier } from 'app/shared/model/chantier.model';

@Injectable({ providedIn: 'root' })
export class ChantierResolve implements Resolve<IChantier> {
    constructor(private service: ChantierService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((chantier: HttpResponse<Chantier>) => chantier.body));
        }
        return of(new Chantier());
    }
}

export const chantierRoute: Routes = [
    {
        path: 'chantier',
        component: ChantierComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Chantiers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chantier/:id/view',
        component: ChantierDetailComponent,
        resolve: {
            chantier: ChantierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chantiers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chantier/new',
        component: ChantierUpdateComponent,
        resolve: {
            chantier: ChantierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chantiers'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chantier/:id/edit',
        component: ChantierUpdateComponent,
        resolve: {
            chantier: ChantierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chantiers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chantierPopupRoute: Routes = [
    {
        path: 'chantier/:id/delete',
        component: ChantierDeletePopupComponent,
        resolve: {
            chantier: ChantierResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Chantiers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
