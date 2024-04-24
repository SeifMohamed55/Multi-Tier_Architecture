export interface IUserRegister{
    firstName : string;
    lastName : string;
    email : string;
    password : string;
    confirmPassword : string;
    address: string;
  }

export type IUserRegisterWithoutConfirmPassword = Omit<IUserRegister, 'confirmPassword'>;