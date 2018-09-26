import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IChantier } from 'app/shared/model/chantier.model';
import { ChantierService } from './chantier.service';

@Component({
    selector: 'jhi-chantier-update',
    templateUrl: './chantier-update.component.html'
})
export class ChantierUpdateComponent implements OnInit {
    private _chantier: IChantier;
    isSaving: boolean;
    dateDebutDp: any;
    dateFinDp: any;

    constructor(
        private dataUtils: JhiDataUtils,
        private chantierService: ChantierService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ chantier }) => {
            this.chantier = chantier;
        });
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
        this.dataUtils.clearInputImage(this.chantier, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.chantier.id !== undefined) {
            this.subscribeToSaveResponse(this.chantierService.update(this.chantier));
        } else {
            this.subscribeToSaveResponse(this.chantierService.create(this.chantier));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IChantier>>) {
        result.subscribe((res: HttpResponse<IChantier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get chantier() {
        return this._chantier;
    }

    set chantier(chantier: IChantier) {
        this._chantier = chantier;
    }
}
