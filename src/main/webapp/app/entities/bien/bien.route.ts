import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Bien } from 'app/shared/model/bien.model';
import { BienService } from './bien.service';
import { BienComponent } from './bien.component';
import { BienDetailComponent } from './bien-detail.component';
import { BienUpdateComponent } from './bien-update.component';
import { BienDeletePopupComponent } from './bien-delete-dialog.component';
import { IBien } from 'app/shared/model/bien.model';

@Injectable({ providedIn: 'root' })
export class BienResolve implements Resolve<IBien> {
    constructor(private service: BienService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((bien: HttpResponse<Bien>) => bien.body));
        }
        return of(new Bien());
    }
}

export const bienRoute: Routes = [
    {
        path: 'bien',
        component: BienComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Biens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bien/:id/view',
        component: BienDetailComponent,
        resolve: {
            bien: BienResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Biens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bien/new',
        component: BienUpdateComponent,
        resolve: {
            bien: BienResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Biens'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'bien/:id/edit',
        component: BienUpdateComponent,
        resolve: {
            bien: BienResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Biens'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bienPopupRoute: Routes = [
    {
        path: 'bien/:id/delete',
        component: BienDeletePopupComponent,
        resolve: {
            bien: BienResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Biens'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
