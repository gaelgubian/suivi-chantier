import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBien } from 'app/shared/model/bien.model';
import { BienService } from './bien.service';
import { IChantier } from 'app/shared/model/chantier.model';
import { ChantierService } from 'app/entities/chantier';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse';

@Component({
    selector: 'jhi-bien-update',
    templateUrl: './bien-update.component.html'
})
export class BienUpdateComponent implements OnInit {
    private _bien: IBien;
    isSaving: boolean;

    chantiers: IChantier[];

    adressechantiers: IAdresse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bienService: BienService,
        private chantierService: ChantierService,
        private adresseService: AdresseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bien }) => {
            this.bien = bien;
        });
        this.chantierService.query().subscribe(
            (res: HttpResponse<IChantier[]>) => {
                this.chantiers = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.adresseService.query({ filter: 'bien-is-null' }).subscribe(
            (res: HttpResponse<IAdresse[]>) => {
                if (!this.bien.adressechantier || !this.bien.adressechantier.id) {
                    this.adressechantiers = res.body;
                } else {
                    this.adresseService.find(this.bien.adressechantier.id).subscribe(
                        (subRes: HttpResponse<IAdresse>) => {
                            this.adressechantiers = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bien.id !== undefined) {
            this.subscribeToSaveResponse(this.bienService.update(this.bien));
        } else {
            this.subscribeToSaveResponse(this.bienService.create(this.bien));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IBien>>) {
        result.subscribe((res: HttpResponse<IBien>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackChantierById(index: number, item: IChantier) {
        return item.id;
    }

    trackAdresseById(index: number, item: IAdresse) {
        return item.id;
    }
    get bien() {
        return this._bien;
    }

    set bien(bien: IBien) {
        this._bien = bien;
    }
}
