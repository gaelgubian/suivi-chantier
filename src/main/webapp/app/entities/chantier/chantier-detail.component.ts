import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IChantier } from 'app/shared/model/chantier.model';

@Component({
    selector: 'jhi-chantier-detail',
    templateUrl: './chantier-detail.component.html'
})
export class ChantierDetailComponent implements OnInit {
    chantier: IChantier;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chantier }) => {
            this.chantier = chantier;
        });
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }
}
