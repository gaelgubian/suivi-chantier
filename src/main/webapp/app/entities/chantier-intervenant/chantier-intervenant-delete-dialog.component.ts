import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';
import { ChantierIntervenantService } from './chantier-intervenant.service';

@Component({
    selector: 'jhi-chantier-intervenant-delete-dialog',
    templateUrl: './chantier-intervenant-delete-dialog.component.html'
})
export class ChantierIntervenantDeleteDialogComponent {
    chantierIntervenant: IChantierIntervenant;

    constructor(
        private chantierIntervenantService: ChantierIntervenantService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.chantierIntervenantService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'chantierIntervenantListModification',
                content: 'Deleted an chantierIntervenant'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-chantier-intervenant-delete-popup',
    template: ''
})
export class ChantierIntervenantDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ chantierIntervenant }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ChantierIntervenantDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.chantierIntervenant = chantierIntervenant;
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
