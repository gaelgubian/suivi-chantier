/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SuiviChantierTestModule } from '../../../test.module';
import { ChantierDeleteDialogComponent } from 'app/entities/chantier/chantier-delete-dialog.component';
import { ChantierService } from 'app/entities/chantier/chantier.service';

describe('Component Tests', () => {
    describe('Chantier Management Delete Component', () => {
        let comp: ChantierDeleteDialogComponent;
        let fixture: ComponentFixture<ChantierDeleteDialogComponent>;
        let service: ChantierService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [ChantierDeleteDialogComponent]
            })
                .overrideTemplate(ChantierDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChantierDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChantierService);
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
