/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { SuiviChantierTestModule } from '../../../test.module';
import { DocumentTuileDetailComponent } from 'app/entities/document-tuile/document-tuile-detail.component';
import { DocumentTuile } from 'app/shared/model/document-tuile.model';

describe('Component Tests', () => {
    describe('DocumentTuile Management Detail Component', () => {
        let comp: DocumentTuileDetailComponent;
        let fixture: ComponentFixture<DocumentTuileDetailComponent>;
        const route = ({ data: of({ documentTuile: new DocumentTuile(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [SuiviChantierTestModule],
                declarations: [DocumentTuileDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DocumentTuileDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DocumentTuileDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.documentTuile).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
