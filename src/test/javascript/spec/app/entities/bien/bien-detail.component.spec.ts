/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { BienDetailComponent } from 'app/entities/bien/bien-detail.component';
import { Bien } from 'app/shared/model/bien.model';

describe('Component Tests', () => {
    describe('Bien Management Detail Component', () => {
        let comp: BienDetailComponent;
        let fixture: ComponentFixture<BienDetailComponent>;
        const route = ({ data: of({ bien: new Bien(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [BienDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(BienDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BienDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.bien).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
