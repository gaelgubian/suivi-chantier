import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { ISignature } from 'app/shared/model/signature.model';

@Component({
    selector: 'jhi-signature-detail',
    templateUrl: './signature-detail.component.html'
})
export class SignatureDetailComponent implements OnInit {
    signature: ISignature;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ signature }) => {
            this.signature = signature;
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
