import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';

@Component({
    selector: 'jhi-chantier-intervenant-detail',
    templateUrl: './chantier-intervenant-detail.component.html'
})
export class ChantierIntervenantDetailComponent implements OnInit {
    chantierIntervenant: IChantierIntervenant;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chantierIntervenant }) => {
            this.chantierIntervenant = chantierIntervenant;
        });
    }

    previousState() {
        window.history.back();
    }
}
