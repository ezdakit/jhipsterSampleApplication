/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { JhipsterSampleApplicationTestModule } from '../../../test.module';
import { DevicesMyPruebaUpdateComponent } from 'app/entities/devices-my-prueba/devices-my-prueba-update.component';
import { DevicesMyPruebaService } from 'app/entities/devices-my-prueba/devices-my-prueba.service';
import { DevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';

describe('Component Tests', () => {
    describe('DevicesMyPrueba Management Update Component', () => {
        let comp: DevicesMyPruebaUpdateComponent;
        let fixture: ComponentFixture<DevicesMyPruebaUpdateComponent>;
        let service: DevicesMyPruebaService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [JhipsterSampleApplicationTestModule],
                declarations: [DevicesMyPruebaUpdateComponent]
            })
                .overrideTemplate(DevicesMyPruebaUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DevicesMyPruebaUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DevicesMyPruebaService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DevicesMyPrueba(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.devices = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DevicesMyPrueba();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.devices = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
