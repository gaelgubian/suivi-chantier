import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ISignature } from 'app/shared/model/signature.model';
import { SignatureService } from './signature.service';
import { IChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';
import { ChantierIntervenantService } from 'app/entities/chantier-intervenant';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';
import { IVisite } from 'app/shared/model/visite.model';
import { VisiteService } from 'app/entities/visite';

@Component({
    selector: 'jhi-signature-update',
    templateUrl: './signature-update.component.html'
})
export class SignatureUpdateComponent implements OnInit {
    private _signature: ISignature;
    isSaving: boolean;

    chantierintervenants: IChantierIntervenant[];

    documents: IDocument[];

    visites: IVisite[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private signatureService: SignatureService,
        private chantierIntervenantService: ChantierIntervenantService,
        private documentService: DocumentService,
        private visiteService: VisiteService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ signature }) => {
            this.signature = signature;
        });
        this.chantierIntervenantService.query({ filter: 'signature-is-null' }).subscribe(
            (res: HttpResponse<IChantierIntervenant[]>) => {
                if (!this.signature.chantierIntervenant || !this.signature.chantierIntervenant.id) {
                    this.chantierintervenants = res.body;
                } else {
                    this.chantierIntervenantService.find(this.signature.chantierIntervenant.id).subscribe(
                        (subRes: HttpResponse<IChantierIntervenant>) => {
                            this.chantierintervenants = [subRes.body].concat(res.body);
                        },
                        (subRes: HttpErrorResponse) => this.onError(subRes.message)
                    );
                }
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.documentService.query().subscribe(
            (res: HttpResponse<IDocument[]>) => {
                this.documents = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.visiteService.query().subscribe(
            (res: HttpResponse<IVisite[]>) => {
                this.visites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clearInputImage(field: string, fieldContentType: string, idInput: string) {
        this.dataUtils.clearInputImage(this.signature, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.signature.id !== undefined) {
            this.subscribeToSaveResponse(this.signatureService.update(this.signature));
        } else {
            this.subscribeToSaveResponse(this.signatureService.create(this.signature));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISignature>>) {
        result.subscribe((res: HttpResponse<ISignature>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackChantierIntervenantById(index: number, item: IChantierIntervenant) {
        return item.id;
    }

    trackDocumentById(index: number, item: IDocument) {
        return item.id;
    }

    trackVisiteById(index: number, item: IVisite) {
        return item.id;
    }
    get signature() {
        return this._signature;
    }

    set signature(signature: ISignature) {
        this._signature = signature;
    }
}
