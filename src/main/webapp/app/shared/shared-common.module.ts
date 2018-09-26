import { NgModule } from '@angular/core';

import { SuiviChantierSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent } from './';

@NgModule({
    imports: [SuiviChantierSharedLibsModule],
    declarations: [JhiAlertComponent, JhiAlertErrorComponent],
    exports: [SuiviChantierSharedLibsModule, JhiAlertComponent, JhiAlertErrorComponent]
})
export class SuiviChantierSharedCommonModule {}
