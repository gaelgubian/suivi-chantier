/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { SignatureDetailComponent } from 'app/entities/signature/signature-detail.component';
import { Signature } from 'app/shared/model/signature.model';

describe('Component Tests', () => {
    describe('Signature Management Detail Component', () => {
        let comp: SignatureDetailComponent;
        let fixture: ComponentFixture<SignatureDetailComponent>;
        const route = ({ data: of({ signature: new Signature(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [SignatureDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SignatureDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SignatureDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.signature).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
