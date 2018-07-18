export interface IDevicesMyPrueba {
    id?: number;
    deviceName?: string;
}

export class DevicesMyPrueba implements IDevicesMyPrueba {
    constructor(public id?: number, public deviceName?: string) {}
}
