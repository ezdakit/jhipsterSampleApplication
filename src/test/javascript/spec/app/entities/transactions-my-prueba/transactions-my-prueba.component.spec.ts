/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { TransactionsMyPruebaComponent } from 'app/entities/transactions-my-prueba/transactions-my-prueba.component';
import { TransactionsMyPruebaService } from 'app/entities/transactions-my-prueba/transactions-my-prueba.service';
import { TransactionsMyPrueba } from 'app/shared/model/transactions-my-prueba.model';

describe('Component Tests', () => {
    describe('TransactionsMyPrueba Management Component', () => {
        let comp: TransactionsMyPruebaComponent;
        let fixture: ComponentFixture<TransactionsMyPruebaComponent>;
        let service: TransactionsMyPruebaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [TransactionsMyPruebaComponent],
                providers: []
            })
                .overrideTemplate(TransactionsMyPruebaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TransactionsMyPruebaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TransactionsMyPruebaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TransactionsMyPrueba(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.transactions[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
