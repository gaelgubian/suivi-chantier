import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IBien } from 'app/shared/model/bien.model';

@Component({
    selector: 'jhi-bien-detail',
    templateUrl: './bien-detail.component.html'
})
export class BienDetailComponent implements OnInit {
    bien: IBien;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bien }) => {
            this.bien = bien;
        });
    }

    previousState() {
        window.history.back();
    }
}
