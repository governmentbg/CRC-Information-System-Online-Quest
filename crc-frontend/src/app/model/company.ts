import { Representative } from "./representative";
import { AuthorizedPerson } from "./authorizedPerson";
import { Address } from "./address";

export class Company {
    id: String;
    pid: string;
    uid: String;
    name: string;
    representative: Representative;
    legalForm: String;
    employeesCount: number;
    authorizedPerson: AuthorizedPerson;
    address: Address;
    companyTrademark: string;
    companyEmailWeb: string;
    phone: string;
    fax: string;
}