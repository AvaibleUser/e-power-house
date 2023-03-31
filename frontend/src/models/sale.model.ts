import SaleDetail from "./sale-detail.model";

export default interface Sale {
  id: number;
  clientNit: string;
  saleDetails: SaleDetail[];
  employeeCui?: string;
  branchId?: number;
  discount?: number;
  date?: Date;
}
