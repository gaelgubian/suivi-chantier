/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { IconDetailComponent } from 'app/entities/icon/icon-detail.component';
import { Icon } from 'app/shared/model/icon.model';

describe('Component Tests', () => {
    describe('Icon Management Detail Component', () => {
        let comp: IconDetailComponent;
        let fixture: ComponentFixture<IconDetailComponent>;
        const route = ({ data: of({ icon: new Icon(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [IconDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(IconDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(IconDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.icon).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
