/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { ChantierUpdateComponent } from 'app/entities/chantier/chantier-update.component';
import { ChantierService } from 'app/entities/chantier/chantier.service';
import { Chantier } from 'app/shared/model/chantier.model';

describe('Component Tests', () => {
    describe('Chantier Management Update Component', () => {
        let comp: ChantierUpdateComponent;
        let fixture: ComponentFixture<ChantierUpdateComponent>;
        let service: ChantierService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [ChantierUpdateComponent]
            })
                .overrideTemplate(ChantierUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChantierUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChantierService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Chantier(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chantier = entity;
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
                    const entity = new Chantier();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chantier = entity;
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
