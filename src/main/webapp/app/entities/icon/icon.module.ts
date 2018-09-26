import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    IconComponent,
    IconDetailComponent,
    IconUpdateComponent,
    IconDeletePopupComponent,
    IconDeleteDialogComponent,
    iconRoute,
    iconPopupRoute
} from './';

const ENTITY_STATES = [...iconRoute, ...iconPopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [IconComponent, IconDetailComponent, IconUpdateComponent, IconDeleteDialogComponent, IconDeletePopupComponent],
    entryComponents: [IconComponent, IconUpdateComponent, IconDeleteDialogComponent, IconDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierIconModule {}
