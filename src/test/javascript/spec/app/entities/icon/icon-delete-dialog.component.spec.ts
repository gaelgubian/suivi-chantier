/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { SuiviChantierTestModule } from '../../../test.module';
import { IconDeleteDialogComponent } from 'app/entities/icon/icon-delete-dialog.component';
import { IconService } from 'app/entities/icon/icon.service';

describe('Component Tests', () => {
    describe('Icon Management Delete Component', () => {
        let comp: IconDeleteDialogComponent;
        let fixture: ComponentFixture<IconDeleteDialogComponent>;
        let service: IconService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [IconDeleteDialogComponent]
            })
                .overrideTemplate(IconDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IconDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IconService);
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
