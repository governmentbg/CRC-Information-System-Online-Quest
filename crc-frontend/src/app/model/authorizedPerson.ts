import { NamePrefixEnum } from "app/enum/namePrefixEnum";
import { SignatureDeviceData } from "./signatureDeviceData";
import { UsernamePassData } from "./usernamePassData";

export class AuthorizedPerson {
    id: number;
    namePrefix: String;
    firstName: String;
    middleName: String;
    lastName: String;
    egn: String;
    email: String;
    signatureDeviceData?: SignatureDeviceData;
    usernamePassData?: UsernamePassData;
}