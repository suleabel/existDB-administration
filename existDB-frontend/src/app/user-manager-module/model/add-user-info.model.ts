export class AddUserInfoModel {
  username: string;
  firstName: string;
  lastName: string;
  email: string;
  password: string;
  administrator: boolean;
  user: boolean;
  projectManager: boolean;

  constructor(username: string, firstName: string, lastName: string, email: string, password: string,
              administrator: boolean, user: boolean, projectManager: boolean) {
    this.username = username;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.administrator = administrator;
    this.user = user;
    this.projectManager = projectManager;
  }
}
