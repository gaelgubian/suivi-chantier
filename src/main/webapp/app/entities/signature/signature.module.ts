import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    SignatureComponent,
    SignatureDetailComponent,
    SignatureUpdateComponent,
    SignatureDeletePopupComponent,
    SignatureDeleteDialogComponent,
    signatureRoute,
    signaturePopupRoute
} from './';

const ENTITY_STATES = [...signatureRoute, ...signaturePopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        SignatureComponent,
        SignatureDetailComponent,
        SignatureUpdateComponent,
        SignatureDeleteDialogComponent,
        SignatureDeletePopupComponent
    ],
    entryComponents: [SignatureComponent, SignatureUpdateComponent, SignatureDeleteDialogComponent, SignatureDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierSignatureModule {}
