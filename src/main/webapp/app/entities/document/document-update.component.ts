import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from './document.service';
import { IVisite } from 'app/shared/model/visite.model';
import { VisiteService } from 'app/entities/visite';
import { IChantier } from 'app/shared/model/chantier.model';
import { ChantierService } from 'app/entities/chantier';
import { IBien } from 'app/shared/model/bien.model';
import { BienService } from 'app/entities/bien';

@Component({
    selector: 'jhi-document-update',
    templateUrl: './document-update.component.html'
})
export class DocumentUpdateComponent implements OnInit {
    private _document: IDocument;
    isSaving: boolean;

    visites: IVisite[];

    chantiers: IChantier[];

    biens: IBien[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private documentService: DocumentService,
        private visiteService: VisiteService,
        private chantierService: ChantierService,
        private bienService: BienService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ document }) => {
            this.document = document;
        });
        this.visiteService.query().subscribe(
            (res: HttpResponse<IVisite[]>) => {
                this.visites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.chantierService.query().subscribe(
            (res: HttpResponse<IChantier[]>) => {
                this.chantiers = res.body;
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
        if (this.document.id !== undefined) {
            this.subscribeToSaveResponse(this.documentService.update(this.document));
        } else {
            this.subscribeToSaveResponse(this.documentService.create(this.document));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDocument>>) {
        result.subscribe((res: HttpResponse<IDocument>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackVisiteById(index: number, item: IVisite) {
        return item.id;
    }

    trackChantierById(index: number, item: IChantier) {
        return item.id;
    }

    trackBienById(index: number, item: IBien) {
        return item.id;
    }
    get document() {
        return this._document;
    }

    set document(document: IDocument) {
        this._document = document;
    }
}
