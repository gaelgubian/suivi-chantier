import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SuiviChantierSharedModule } from 'app/shared';
import {
    DocumentTuileComponent,
    DocumentTuileDetailComponent,
    DocumentTuileUpdateComponent,
    DocumentTuileDeletePopupComponent,
    DocumentTuileDeleteDialogComponent,
    documentTuileRoute,
    documentTuilePopupRoute
} from './';

const ENTITY_STATES = [...documentTuileRoute, ...documentTuilePopupRoute];

@NgModule({
    imports: [SuiviChantierSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        DocumentTuileComponent,
        DocumentTuileDetailComponent,
        DocumentTuileUpdateComponent,
        DocumentTuileDeleteDialogComponent,
        DocumentTuileDeletePopupComponent
    ],
    entryComponents: [
        DocumentTuileComponent,
        DocumentTuileUpdateComponent,
        DocumentTuileDeleteDialogComponent,
        DocumentTuileDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierDocumentTuileModule {}
