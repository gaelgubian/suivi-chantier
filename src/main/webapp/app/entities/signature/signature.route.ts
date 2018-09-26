import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { Signature } from 'app/shared/model/signature.model';
import { SignatureService } from './signature.service';
import { SignatureComponent } from './signature.component';
import { SignatureDetailComponent } from './signature-detail.component';
import { SignatureUpdateComponent } from './signature-update.component';
import { SignatureDeletePopupComponent } from './signature-delete-dialog.component';
import { ISignature } from 'app/shared/model/signature.model';

@Injectable({ providedIn: 'root' })
export class SignatureResolve implements Resolve<ISignature> {
    constructor(private service: SignatureService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((signature: HttpResponse<Signature>) => signature.body));
        }
        return of(new Signature());
    }
}

export const signatureRoute: Routes = [
    {
        path: 'signature',
        component: SignatureComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'Signatures'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'signature/:id/view',
        component: SignatureDetailComponent,
        resolve: {
            signature: SignatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Signatures'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'signature/new',
        component: SignatureUpdateComponent,
        resolve: {
            signature: SignatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Signatures'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'signature/:id/edit',
        component: SignatureUpdateComponent,
        resolve: {
            signature: SignatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Signatures'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const signaturePopupRoute: Routes = [
    {
        path: 'signature/:id/delete',
        component: SignatureDeletePopupComponent,
        resolve: {
            signature: SignatureResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Signatures'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
