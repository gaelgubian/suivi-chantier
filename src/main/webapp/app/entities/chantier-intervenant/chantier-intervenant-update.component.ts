import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';
import { ChantierIntervenantService } from './chantier-intervenant.service';
import { IContact } from 'app/shared/model/contact.model';
import { ContactService } from 'app/entities/contact';
import { ISignature } from 'app/shared/model/signature.model';
import { SignatureService } from 'app/entities/signature';
import { IChantier } from 'app/shared/model/chantier.model';
import { ChantierService } from 'app/entities/chantier';

@Component({
    selector: 'jhi-chantier-intervenant-update',
    templateUrl: './chantier-intervenant-update.component.html'
})
export class ChantierIntervenantUpdateComponent implements OnInit {
    private _chantierIntervenant: IChantierIntervenant;
    isSaving: boolean;

    contacts: IContact[];

    signatures: ISignature[];

    chantiers: IChantier[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private chantierIntervenantService: ChantierIntervenantService,
        private contactService: ContactService,
        private signatureService: SignatureService,
        private chantierService: ChantierService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chantierIntervenant }) => {
            this.chantierIntervenant = chantierIntervenant;
        });
        this.contactService.query({ filter: 'chantierintervenant-is-null' }).subscribe(
            (res: HttpResponse<IContact[]>) => {
                if (!this.chantierIntervenant.contact || !this.chantierIntervenant.contact.id) {
                    this.contacts = res.body;
                } else {
                    this.contactService.find(this.chantierIntervenant.contact.id).subscribe(
                        (subRes: HttpResponse<IContact>) => {
                            this.contacts = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.signatureService.query().subscribe(
            (res: HttpResponse<ISignature[]>) => {
                this.signatures = res.body;
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
        if (this.chantierIntervenant.id !== undefined) {
            this.subscribeToSaveResponse(this.chantierIntervenantService.update(this.chantierIntervenant));
        } else {
            this.subscribeToSaveResponse(this.chantierIntervenantService.create(this.chantierIntervenant));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChantierIntervenant>>) {
        result.subscribe((res: HttpResponse<IChantierIntervenant>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSignatureById(index: number, item: ISignature) {
        return item.id;
    }

    trackChantierById(index: number, item: IChantier) {
        return item.id;
    }
    get chantierIntervenant() {
        return this._chantierIntervenant;
    }

    set chantierIntervenant(chantierIntervenant: IChantierIntervenant) {
        this._chantierIntervenant = chantierIntervenant;
    }
}
