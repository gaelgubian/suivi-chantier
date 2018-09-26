import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { JhiDataUtils } from 'ng-jhipster';

import { IIcon } from 'app/shared/model/icon.model';

@Component({
    selector: 'jhi-icon-detail',
    templateUrl: './icon-detail.component.html'
})
export class IconDetailComponent implements OnInit {
    icon: IIcon;

    constructor(private dataUtils: JhiDataUtils, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ icon }) => {
            this.icon = icon;
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
