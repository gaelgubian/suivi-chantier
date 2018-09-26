/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { ChantierIntervenantUpdateComponent } from 'app/entities/chantier-intervenant/chantier-intervenant-update.component';
import { ChantierIntervenantService } from 'app/entities/chantier-intervenant/chantier-intervenant.service';
import { ChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';

describe('Component Tests', () => {
    describe('ChantierIntervenant Management Update Component', () => {
        let comp: ChantierIntervenantUpdateComponent;
        let fixture: ComponentFixture<ChantierIntervenantUpdateComponent>;
        let service: ChantierIntervenantService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [ChantierIntervenantUpdateComponent]
            })
                .overrideTemplate(ChantierIntervenantUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ChantierIntervenantUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ChantierIntervenantService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ChantierIntervenant(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chantierIntervenant = entity;
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
                    const entity = new ChantierIntervenant();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.chantierIntervenant = entity;
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
