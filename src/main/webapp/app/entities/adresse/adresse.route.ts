import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Adresse } from 'app/shared/model/adresse.model';
import { AdresseService } from './adresse.service';
import { AdresseComponent } from './adresse.component';
import { AdresseDetailComponent } from './adresse-detail.component';
import { AdresseUpdateComponent } from './adresse-update.component';
import { AdresseDeletePopupComponent } from './adresse-delete-dialog.component';
import { IAdresse } from 'app/shared/model/adresse.model';

@Injectable({ providedIn: 'root' })
export class AdresseResolve implements Resolve<IAdresse> {
    constructor(private service: AdresseService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((adresse: HttpResponse<Adresse>) => adresse.body));
        }
        return of(new Adresse());
    }
}

export const adresseRoute: Routes = [
    {
        path: 'adresse',
        component: AdresseComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Adresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adresse/:id/view',
        component: AdresseDetailComponent,
        resolve: {
            adresse: AdresseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Adresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adresse/new',
        component: AdresseUpdateComponent,
        resolve: {
            adresse: AdresseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Adresses'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'adresse/:id/edit',
        component: AdresseUpdateComponent,
        resolve: {
            adresse: AdresseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Adresses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const adressePopupRoute: Routes = [
    {
        path: 'adresse/:id/delete',
        component: AdresseDeletePopupComponent,
        resolve: {
            adresse: AdresseResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Adresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
