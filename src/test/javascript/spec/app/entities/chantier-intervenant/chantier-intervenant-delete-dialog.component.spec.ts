/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SuiviChantierTestModule } from '../../../test.module';
import { ChantierIntervenantDeleteDialogComponent } from 'app/entities/chantier-intervenant/chantier-intervenant-delete-dialog.component';
import { ChantierIntervenantService } from 'app/entities/chantier-intervenant/chantier-intervenant.service';

describe('Component Tests', () => {
    describe('ChantierIntervenant Management Delete Component', () => {
        let comp: ChantierIntervenantDeleteDialogComponent;
        let fixture: ComponentFixture<ChantierIntervenantDeleteDialogComponent>;
        let service: ChantierIntervenantService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [ChantierIntervenantDeleteDialogComponent]
            })
                .overrideTemplate(ChantierIntervenantDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChantierIntervenantDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChantierIntervenantService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
