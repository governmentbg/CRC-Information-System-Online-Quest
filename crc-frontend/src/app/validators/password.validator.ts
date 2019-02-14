import {AbstractControl} from '@angular/forms';

export class ValidatePassword{

    static validate(control: AbstractControl){
        let password = control.get('password').value;
        let confirmPassword = control.get('repeatPassword').value;

        if(password === confirmPassword){
            return null;
        }
        else{
            control.get('repeatPassword').setErrors({validate: true});
        }
    }
}