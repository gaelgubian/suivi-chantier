import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';
import { JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { IComment } from 'app/shared/model/comment.model';
import { CommentService } from './comment.service';
import { IIcon } from 'app/shared/model/icon.model';
import { IconService } from 'app/entities/icon';
import { IVisite } from 'app/shared/model/visite.model';
import { VisiteService } from 'app/entities/visite';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';

@Component({
    selector: 'jhi-comment-update',
    templateUrl: './comment-update.component.html'
})
export class CommentUpdateComponent implements OnInit {
    private _comment: IComment;
    isSaving: boolean;

    icons: IIcon[];

    visites: IVisite[];

    documents: IDocument[];
    modified: string;
    echeanceDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private commentService: CommentService,
        private iconService: IconService,
        private visiteService: VisiteService,
        private documentService: DocumentService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ comment }) => {
            this.comment = comment;
        });
        this.iconService.query().subscribe(
            (res: HttpResponse<IIcon[]>) => {
                this.icons = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.visiteService.query().subscribe(
            (res: HttpResponse<IVisite[]>) => {
                this.visites = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
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
        this.dataUtils.clearInputImage(this.comment, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        this.comment.modified = moment(this.modified, DATE_TIME_FORMAT);
        if (this.comment.id !== undefined) {
            this.subscribeToSaveResponse(this.commentService.update(this.comment));
        } else {
            this.subscribeToSaveResponse(this.commentService.create(this.comment));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IComment>>) {
        result.subscribe((res: HttpResponse<IComment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackIconById(index: number, item: IIcon) {
        return item.id;
    }

    trackVisiteById(index: number, item: IVisite) {
        return item.id;
    }

    trackDocumentById(index: number, item: IDocument) {
        return item.id;
    }
    get comment() {
        return this._comment;
    }

    set comment(comment: IComment) {
        this._comment = comment;
        this.modified = moment(comment.modified).format(DATE_TIME_FORMAT);
    }
}
