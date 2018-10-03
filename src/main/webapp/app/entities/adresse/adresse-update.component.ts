import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from './adresse.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact';
import { IBien } from 'app/shared/model/bien.model';
import { BienService } from 'app/entities/bien';

@Component({
    selector: 'jhi-adresse-update',
    templateUrl: './adresse-update.component.html'
})
export class AdresseUpdateComponent implements OnInit {
    private _adresse: IAdresse;
    isSaving: boolean;

    contacts: IContact[];

    biens: IBien[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private adresseService: AdresseService,
        private contactService: ContactService,
        private bienService: BienService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ adresse }) => {
            this.adresse = adresse;
        });
        this.contactService.query().subscribe(
            (res: HttpResponse<IContact[]>) => {
                this.contacts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.bienService.query().subscribe(
            (res: HttpResponse<IBien[]>) => {
                this.biens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackContactById(index: number, item: IContact) {
        return item.id;
    }

    trackBienById(index: number, item: IBien) {
        return item.id;
    }
    get adresse() {
        return this._adresse;
    }

    set adresse(adresse: IAdresse) {
        this._adresse = adresse;
    }
}
