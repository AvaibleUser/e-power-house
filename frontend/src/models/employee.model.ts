import JobTitle from "./job-title.enum";

export default interface Employee {
  cui: string;
  branchId: number;
  warehouseId: number;
  jobTitle: JobTitle;
  name: string | undefined;
  lastName: string | undefined;
  password: string | undefined;
  birthdate: Date | undefined;
  address: string | undefined;
  email: string | undefined;
  phone: string | undefined;
}
