import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from './contact.service';
import { IAdresse } from 'app/shared/model/adresse.model';
import { AdresseService } from 'app/entities/adresse';

@Component({
    selector: 'jhi-contact-update',
    templateUrl: './contact-update.component.html'
})
export class ContactUpdateComponent implements OnInit {
    private _contact: IContact;
    isSaving: boolean;

    adresses: IAdresse[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private contactService: ContactService,
        private adresseService: AdresseService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ contact }) => {
            this.contact = contact;
        });
        this.adresseService.query({ filter: 'contact(label)-is-null' }).subscribe(
            (res: HttpResponse<IAdresse[]>) => {
                if (!this.contact.adresse || !this.contact.adresse.id) {
                    this.adresses = res.body;
                } else {
                    this.adresseService.find(this.contact.adresse.id).subscribe(
                        (subRes: HttpResponse<IAdresse>) => {
                            this.adresses = [subRes.body].concat(res.body);
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
        if (this.contact.id !== undefined) {
            this.subscribeToSaveResponse(this.contactService.update(this.contact));
        } else {
            this.subscribeToSaveResponse(this.contactService.create(this.contact));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IContact>>) {
        result.subscribe((res: HttpResponse<IContact>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
    get contact() {
        return this._contact;
    }

    set contact(contact: IContact) {
        this._contact = contact;
    }
}
