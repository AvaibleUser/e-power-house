export default interface Product {
  id: number;
  productName: string;
  unitPrice: number;
  amount: number;
  description: string;
  setShoppingCart?: Function;
}
