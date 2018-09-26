import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDocumentTuile } from 'app/shared/model/document-tuile.model';
import { DocumentTuileService } from './document-tuile.service';

@Component({
    selector: 'jhi-document-tuile-delete-dialog',
    templateUrl: './document-tuile-delete-dialog.component.html'
})
export class DocumentTuileDeleteDialogComponent {
    documentTuile: IDocumentTuile;

    constructor(
        private documentTuileService: DocumentTuileService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.documentTuileService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'documentTuileListModification',
                content: 'Deleted an documentTuile'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-document-tuile-delete-popup',
    template: ''
})
export class DocumentTuileDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ documentTuile }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DocumentTuileDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.documentTuile = documentTuile;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
