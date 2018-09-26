/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { DocumentTuileUpdateComponent } from 'app/entities/document-tuile/document-tuile-update.component';
import { DocumentTuileService } from 'app/entities/document-tuile/document-tuile.service';
import { DocumentTuile } from 'app/shared/model/document-tuile.model';

describe('Component Tests', () => {
    describe('DocumentTuile Management Update Component', () => {
        let comp: DocumentTuileUpdateComponent;
        let fixture: ComponentFixture<DocumentTuileUpdateComponent>;
        let service: DocumentTuileService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [DocumentTuileUpdateComponent]
            })
                .overrideTemplate(DocumentTuileUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DocumentTuileUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DocumentTuileService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DocumentTuile(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTuile = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DocumentTuile();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.documentTuile = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
