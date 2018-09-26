import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Icon } from 'app/shared/model/icon.model';
import { IconService } from './icon.service';
import { IconComponent } from './icon.component';
import { IconDetailComponent } from './icon-detail.component';
import { IconUpdateComponent } from './icon-update.component';
import { IconDeletePopupComponent } from './icon-delete-dialog.component';
import { IIcon } from 'app/shared/model/icon.model';

@Injectable({ providedIn: 'root' })
export class IconResolve implements Resolve<IIcon> {
    constructor(private service: IconService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((icon: HttpResponse<Icon>) => icon.body));
        }
        return of(new Icon());
    }
}

export const iconRoute: Routes = [
    {
        path: 'icon',
        component: IconComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Icons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'icon/:id/view',
        component: IconDetailComponent,
        resolve: {
            icon: IconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Icons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'icon/new',
        component: IconUpdateComponent,
        resolve: {
            icon: IconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Icons'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'icon/:id/edit',
        component: IconUpdateComponent,
        resolve: {
            icon: IconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Icons'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iconPopupRoute: Routes = [
    {
        path: 'icon/:id/delete',
        component: IconDeletePopupComponent,
        resolve: {
            icon: IconResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Icons'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
