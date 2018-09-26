import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IDocumentTuile } from 'app/shared/model/document-tuile.model';

@Component({
    selector: 'jhi-document-tuile-detail',
    templateUrl: './document-tuile-detail.component.html'
})
export class DocumentTuileDetailComponent implements OnInit {
    documentTuile: IDocumentTuile;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTuile }) => {
            this.documentTuile = documentTuile;
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
