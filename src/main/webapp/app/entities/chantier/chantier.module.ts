import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    ChantierComponent,
    ChantierDetailComponent,
    ChantierUpdateComponent,
    ChantierDeletePopupComponent,
    ChantierDeleteDialogComponent,
    chantierRoute,
    chantierPopupRoute
} from './';

const ENTITY_STATES = [...chantierRoute, ...chantierPopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ChantierComponent,
        ChantierDetailComponent,
        ChantierUpdateComponent,
        ChantierDeleteDialogComponent,
        ChantierDeletePopupComponent
    ],
    entryComponents: [ChantierComponent, ChantierUpdateComponent, ChantierDeleteDialogComponent, ChantierDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierChantierModule {}
