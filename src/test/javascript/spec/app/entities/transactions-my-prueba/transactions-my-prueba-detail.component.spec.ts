/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransactionsMyPruebaDetailComponent } from 'app/entities/transactions-my-prueba/transactions-my-prueba-detail.component';
import { TransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';

describe('Component Tests', () => {
    describe('TransactionsMyPrueba Management Detail Component', () => {
        let comp: TransactionsMyPruebaDetailComponent;
        let fixture: ComponentFixture<TransactionsMyPruebaDetailComponent>;
        const route = ({ data: of({ transactions: new TransactionsMyPrueba(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TransactionsMyPruebaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TransactionsMyPruebaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TransactionsMyPruebaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.transactions).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
