import { Role } from "./role";
import { UsernamePassData } from "./usernamePassData";

export class User {
    id: string;
    firstName: String;
    middleName: String;
    lastName: string;
    egn: String;
    usernamePassData?: UsernamePassData;
    email: String;
    activeFrom: Date;
    activeTo: Date;
    roles: String[] = [];
    activationStatus:boolean;
    companyToServe: number[] = [];
}
