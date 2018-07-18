/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DevicesMyPruebaComponent } from 'app/entities/devices-my-prueba/devices-my-prueba.component';
import { DevicesMyPruebaService } from 'app/entities/devices-my-prueba/devices-my-prueba.service';
import { DevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';

describe('Component Tests', () => {
    describe('DevicesMyPrueba Management Component', () => {
        let comp: DevicesMyPruebaComponent;
        let fixture: ComponentFixture<DevicesMyPruebaComponent>;
        let service: DevicesMyPruebaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DevicesMyPruebaComponent],
                providers: []
            })
                .overrideTemplate(DevicesMyPruebaComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DevicesMyPruebaComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevicesMyPruebaService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DevicesMyPrueba(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.devices[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
