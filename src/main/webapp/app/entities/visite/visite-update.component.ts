import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IVisite } from 'app/shared/model/visite.model';
import { VisiteService } from './visite.service';
import { IBien } from 'app/shared/model/bien.model';
import { BienService } from 'app/entities/bien';
import { IDocument } from 'app/shared/model/document.model';
import { DocumentService } from 'app/entities/document';

@Component({
    selector: 'jhi-visite-update',
    templateUrl: './visite-update.component.html'
})
export class VisiteUpdateComponent implements OnInit {
    private _visite: IVisite;
    isSaving: boolean;

    biens: IBien[];

    documents: IDocument[];
    dateDp: any;

    constructor(
        private jhiAlertService: JhiAlertService,
        private visiteService: VisiteService,
        private bienService: BienService,
        private documentService: DocumentService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ visite }) => {
            this.visite = visite;
        });
        this.bienService.query().subscribe(
            (res: HttpResponse<IBien[]>) => {
                this.biens = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
        this.documentService.query({ filter: 'visite-is-null' }).subscribe(
            (res: HttpResponse<IDocument[]>) => {
                if (!this.visite.document || !this.visite.document.id) {
                    this.documents = res.body;
                } else {
                    this.documentService.find(this.visite.document.id).subscribe(
                        (subRes: HttpResponse<IDocument>) => {
                            this.documents = [subRes.body].concat(res.body);
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
        if (this.visite.id !== undefined) {
            this.subscribeToSaveResponse(this.visiteService.update(this.visite));
        } else {
            this.subscribeToSaveResponse(this.visiteService.create(this.visite));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IVisite>>) {
        result.subscribe((res: HttpResponse<IVisite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBienById(index: number, item: IBien) {
        return item.id;
    }

    trackDocumentById(index: number, item: IDocument) {
        return item.id;
    }
    get visite() {
        return this._visite;
    }

    set visite(visite: IVisite) {
        this._visite = visite;
    }
}
