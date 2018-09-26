import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    AdresseComponent,
    AdresseDetailComponent,
    AdresseUpdateComponent,
    AdresseDeletePopupComponent,
    AdresseDeleteDialogComponent,
    adresseRoute,
    adressePopupRoute
} from './';

const ENTITY_STATES = [...adresseRoute, ...adressePopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AdresseComponent,
        AdresseDetailComponent,
        AdresseUpdateComponent,
        AdresseDeleteDialogComponent,
        AdresseDeletePopupComponent
    ],
    entryComponents: [AdresseComponent, AdresseUpdateComponent, AdresseDeleteDialogComponent, AdresseDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierAdresseModule {}
