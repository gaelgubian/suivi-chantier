import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { SuiviChantierChantierModule } from './chantier/chantier.module';
import { SuiviChantierChantierIntervenantModule } from './chantier-intervenant/chantier-intervenant.module';
import { SuiviChantierBienModule } from './bien/bien.module';
import { SuiviChantierAdresseModule } from './adresse/adresse.module';
import { SuiviChantierDocumentModule } from './document/document.module';
import { SuiviChantierDocumentTuileModule } from './document-tuile/document-tuile.module';
import { SuiviChantierSignatureModule } from './signature/signature.module';
import { SuiviChantierCommentModule } from './comment/comment.module';
import { SuiviChantierIconModule } from './icon/icon.module';
import { SuiviChantierContactModule } from './contact/contact.module';
import { SuiviChantierVisiteModule } from './visite/visite.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        SuiviChantierChantierModule,
        SuiviChantierChantierIntervenantModule,
        SuiviChantierBienModule,
        SuiviChantierAdresseModule,
        SuiviChantierDocumentModule,
        SuiviChantierDocumentTuileModule,
        SuiviChantierSignatureModule,
        SuiviChantierCommentModule,
        SuiviChantierIconModule,
        SuiviChantierContactModule,
        SuiviChantierVisiteModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SuiviChantierEntityModule {}
