/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { ChantierDetailComponent } from 'app/entities/chantier/chantier-detail.component';
import { Chantier } from 'app/shared/model/chantier.model';

describe('Component Tests', () => {
    describe('Chantier Management Detail Component', () => {
        let comp: ChantierDetailComponent;
        let fixture: ComponentFixture<ChantierDetailComponent>;
        const route = ({ data: of({ chantier: new Chantier(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [ChantierDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChantierDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChantierDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chantier).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
