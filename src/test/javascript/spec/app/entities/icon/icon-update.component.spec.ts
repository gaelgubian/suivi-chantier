/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { IconUpdateComponent } from 'app/entities/icon/icon-update.component';
import { IconService } from 'app/entities/icon/icon.service';
import { Icon } from 'app/shared/model/icon.model';

describe('Component Tests', () => {
    describe('Icon Management Update Component', () => {
        let comp: IconUpdateComponent;
        let fixture: ComponentFixture<IconUpdateComponent>;
        let service: IconService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [IconUpdateComponent]
            })
                .overrideTemplate(IconUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(IconUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(IconService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Icon(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.icon = entity;
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
                    const entity = new Icon();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.icon = entity;
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
