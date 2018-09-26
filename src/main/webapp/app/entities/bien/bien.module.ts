import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    BienComponent,
    BienDetailComponent,
    BienUpdateComponent,
    BienDeletePopupComponent,
    BienDeleteDialogComponent,
    bienRoute,
    bienPopupRoute
} from './';

const ENTITY_STATES = [...bienRoute, ...bienPopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [BienComponent, BienDetailComponent, BienUpdateComponent, BienDeleteDialogComponent, BienDeletePopupComponent],
    entryComponents: [BienComponent, BienUpdateComponent, BienDeleteDialogComponent, BienDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierBienModule {}
