/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DevicesMyPruebaDetailComponent } from 'app/entities/devices-my-prueba/devices-my-prueba-detail.component';
import { DevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';

describe('Component Tests', () => {
    describe('DevicesMyPrueba Management Detail Component', () => {
        let comp: DevicesMyPruebaDetailComponent;
        let fixture: ComponentFixture<DevicesMyPruebaDetailComponent>;
        const route = ({ data: of({ devices: new DevicesMyPrueba(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DevicesMyPruebaDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DevicesMyPruebaDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DevicesMyPruebaDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.devices).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
