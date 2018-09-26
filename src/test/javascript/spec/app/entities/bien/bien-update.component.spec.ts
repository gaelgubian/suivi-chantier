/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { BienUpdateComponent } from 'app/entities/bien/bien-update.component';
import { BienService } from 'app/entities/bien/bien.service';
import { Bien } from 'app/shared/model/bien.model';

describe('Component Tests', () => {
    describe('Bien Management Update Component', () => {
        let comp: BienUpdateComponent;
        let fixture: ComponentFixture<BienUpdateComponent>;
        let service: BienService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [BienUpdateComponent]
            })
                .overrideTemplate(BienUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BienUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BienService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Bien(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bien = entity;
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
                    const entity = new Bien();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bien = entity;
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
