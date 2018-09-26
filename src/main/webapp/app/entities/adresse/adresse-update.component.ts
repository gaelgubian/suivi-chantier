import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from './adresse.service';

@Component({
    selector: 'jhi-adresse-update',
    templateUrl: './adresse-update.component.html'
})
export class AdresseUpdateComponent implements OnInit {
    private _adresse: IAdresse;
    isSaving: boolean;

    constructor(private adresseService: AdresseService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adresse }) => {
            this.adresse = adresse;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.adresse.id !== undefined) {
            this.subscribeToSaveResponse(this.adresseService.update(this.adresse));
        } else {
            this.subscribeToSaveResponse(this.adresseService.create(this.adresse));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IAdresse>>) {
        result.subscribe((res: HttpResponse<IAdresse>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get adresse() {
        return this._adresse;
    }

    set adresse(adresse: IAdresse) {
        this._adresse = adresse;
    }
}
