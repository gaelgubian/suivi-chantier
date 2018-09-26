/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { ChantierIntervenantDetailComponent } from 'app/entities/chantier-intervenant/chantier-intervenant-detail.component';
import { ChantierIntervenant } from 'app/shared/model/chantier-intervenant.model';

describe('Component Tests', () => {
    describe('ChantierIntervenant Management Detail Component', () => {
        let comp: ChantierIntervenantDetailComponent;
        let fixture: ComponentFixture<ChantierIntervenantDetailComponent>;
        const route = ({ data: of({ chantierIntervenant: new ChantierIntervenant(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [ChantierIntervenantDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ChantierIntervenantDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ChantierIntervenantDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.chantierIntervenant).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
