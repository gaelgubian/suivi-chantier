import { Component, OnInit, ElementRef } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiDataUtils } from 'ng-jhipster';

import { IIcon } from 'app/shared/model/icon.model';
import { IconService } from './icon.service';

@Component({
    selector: 'jhi-icon-update',
    templateUrl: './icon-update.component.html'
})
export class IconUpdateComponent implements OnInit {
    private _icon: IIcon;
    isSaving: boolean;

    constructor(
        private dataUtils: JhiDataUtils,
        private iconService: IconService,
        private elementRef: ElementRef,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ icon }) => {
            this.icon = icon;
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
        this.dataUtils.clearInputImage(this.icon, this.elementRef, field, fieldContentType, idInput);
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.icon.id !== undefined) {
            this.subscribeToSaveResponse(this.iconService.update(this.icon));
        } else {
            this.subscribeToSaveResponse(this.iconService.create(this.icon));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIcon>>) {
        result.subscribe((res: HttpResponse<IIcon>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get icon() {
        return this._icon;
    }

    set icon(icon: IIcon) {
        this._icon = icon;
    }
}
