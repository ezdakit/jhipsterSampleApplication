import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDevicesMyPrueba } from 'app/shared/model/devices-my-prueba.model';

type EntityResponseType = HttpResponse<IDevicesMyPrueba>;
type EntityArrayResponseType = HttpResponse<IDevicesMyPrueba[]>;

@Injectable({ providedIn: 'root' })
export class DevicesMyPruebaService {
    private resourceUrl = SERVER_API_URL + 'api/devices';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/devices';

    constructor(private http: HttpClient) {}

    create(devices: IDevicesMyPrueba): Observable<EntityResponseType> {
        return this.http.post<IDevicesMyPrueba>(this.resourceUrl, devices, { observe: 'response' });
    }

    update(devices: IDevicesMyPrueba): Observable<EntityResponseType> {
        return this.http.put<IDevicesMyPrueba>(this.resourceUrl, devices, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDevicesMyPrueba>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDevicesMyPrueba[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDevicesMyPrueba[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
