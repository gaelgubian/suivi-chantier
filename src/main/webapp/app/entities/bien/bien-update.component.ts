import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IBien } from 'app/shared/model/bien.model';
import { BienService } from './bien.service';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse';
import { IChantier } from 'app/shared/model/chantier.model';
import { ChantierService } from 'app/entities/chantier';

@Component({
    selector: 'jhi-bien-update',
    templateUrl: './bien-update.component.html'
})
export class BienUpdateComponent implements OnInit {
    private _bien: IBien;
    isSaving: boolean;

    adressebiens: IAdresse[];

    chantiers: IChantier[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private bienService: BienService,
        private adresseService: AdresseService,
        private chantierService: ChantierService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bien }) => {
            this.bien = bien;
        });
        this.adresseService.query({ filter: 'bien(label)-is-null' }).subscribe(
            (res: HttpResponse<IAdresse[]>) => {
                if (!this.bien.adresseBien || !this.bien.adresseBien.id) {
                    this.adressebiens = res.body;
                } else {
                    this.adresseService.find(this.bien.adresseBien.id).subscribe(
                        (subRes: HttpResponse<IAdresse>) => {
                            this.adressebiens = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.chantierService.query().subscribe(
            (res: HttpResponse<IChantier[]>) => {
                this.chantiers = res.body;
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

    trackAdresseById(index: number, item: IAdresse) {
        return item.id;
    }

    trackChantierById(index: number, item: IChantier) {
        return item.id;
    }
    get bien() {
        return this._bien;
    }

    set bien(bien: IBien) {
        this._bien = bien;
    }
}
