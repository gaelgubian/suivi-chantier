import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    VisiteComponent,
    VisiteDetailComponent,
    VisiteUpdateComponent,
    VisiteDeletePopupComponent,
    VisiteDeleteDialogComponent,
    visiteRoute,
    visitePopupRoute
} from './';

const ENTITY_STATES = [...visiteRoute, ...visitePopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [VisiteComponent, VisiteDetailComponent, VisiteUpdateComponent, VisiteDeleteDialogComponent, VisiteDeletePopupComponent],
    entryComponents: [VisiteComponent, VisiteUpdateComponent, VisiteDeleteDialogComponent, VisiteDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierVisiteModule {}
