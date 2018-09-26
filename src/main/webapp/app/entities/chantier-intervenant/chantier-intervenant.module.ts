import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    ChantierIntervenantComponent,
    ChantierIntervenantDetailComponent,
    ChantierIntervenantUpdateComponent,
    ChantierIntervenantDeletePopupComponent,
    ChantierIntervenantDeleteDialogComponent,
    chantierIntervenantRoute,
    chantierIntervenantPopupRoute
} from './';

const ENTITY_STATES = [...chantierIntervenantRoute, ...chantierIntervenantPopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChantierIntervenantComponent,
        ChantierIntervenantDetailComponent,
        ChantierIntervenantUpdateComponent,
        ChantierIntervenantDeleteDialogComponent,
        ChantierIntervenantDeletePopupComponent
    ],
    entryComponents: [
        ChantierIntervenantComponent,
        ChantierIntervenantUpdateComponent,
        ChantierIntervenantDeleteDialogComponent,
        ChantierIntervenantDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierChantierIntervenantModule {}
