import { AbstractControl, ValidationErrors, ValidatorFn } from '@angular/forms';

export function confirmPasswordValidator(controlName: string): ValidatorFn {
  return (control: AbstractControl): ValidationErrors | null => {
    const password = control.parent?.get(controlName);
    const confirmPassword = control.value;

    if (password && password.value !== confirmPassword) {
      return { confirmPassword: true };
    }
    return null;
  };
}
