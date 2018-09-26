import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IDocumentTuile } from 'app/shared/model/document-tuile.model';
import { DocumentTuileService } from './document-tuile.service';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';

@Component({
    selector: 'jhi-document-tuile-update',
    templateUrl: './document-tuile-update.component.html'
})
export class DocumentTuileUpdateComponent implements OnInit {
    private _documentTuile: IDocumentTuile;
    isSaving: boolean;

    documents: IDocument[];

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private documentTuileService: DocumentTuileService,
        private documentService: DocumentService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ documentTuile }) => {
            this.documentTuile = documentTuile;
        });
        this.documentService.query().subscribe(
            (res: HttpResponse<IDocument[]>) => {
                this.documents = res.body;
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
        this.dataUtils.clearInputImage(this.documentTuile, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.documentTuile.id !== undefined) {
            this.subscribeToSaveResponse(this.documentTuileService.update(this.documentTuile));
        } else {
            this.subscribeToSaveResponse(this.documentTuileService.create(this.documentTuile));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IDocumentTuile>>) {
        result.subscribe((res: HttpResponse<IDocumentTuile>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackDocumentById(index: number, item: IDocument) {
        return item.id;
    }
    get documentTuile() {
        return this._documentTuile;
    }

    set documentTuile(documentTuile: IDocumentTuile) {
        this._documentTuile = documentTuile;
    }
}
