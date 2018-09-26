import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';
import { ChantierIntervenantService } from './chantier-intervenant.service';
import { ChantierIntervenantComponent } from './chantier-intervenant.component';
import { ChantierIntervenantDetailComponent } from './chantier-intervenant-detail.component';
import { ChantierIntervenantUpdateComponent } from './chantier-intervenant-update.component';
import { ChantierIntervenantDeletePopupComponent } from './chantier-intervenant-delete-dialog.component';
import { IChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';

@Injectable({ providedIn: 'root' })
export class ChantierIntervenantResolve implements Resolve<IChantierIntervenant> {
    constructor(private service: ChantierIntervenantService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((chantierIntervenant: HttpResponse<ChantierIntervenant>) => chantierIntervenant.body));
        }
        return of(new ChantierIntervenant());
    }
}

export const chantierIntervenantRoute: Routes = [
    {
        path: 'chantier-intervenant',
        component: ChantierIntervenantComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'ChantierIntervenants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chantier-intervenant/:id/view',
        component: ChantierIntervenantDetailComponent,
        resolve: {
            chantierIntervenant: ChantierIntervenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChantierIntervenants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chantier-intervenant/new',
        component: ChantierIntervenantUpdateComponent,
        resolve: {
            chantierIntervenant: ChantierIntervenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChantierIntervenants'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'chantier-intervenant/:id/edit',
        component: ChantierIntervenantUpdateComponent,
        resolve: {
            chantierIntervenant: ChantierIntervenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChantierIntervenants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const chantierIntervenantPopupRoute: Routes = [
    {
        path: 'chantier-intervenant/:id/delete',
        component: ChantierIntervenantDeletePopupComponent,
        resolve: {
            chantierIntervenant: ChantierIntervenantResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChantierIntervenants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
