import React, { useEffect } from "react";
import ProductModel from "../models/product.model";
import { useState } from "react";

const normalStyle = "btn btn-square";
const disabledStyle = "btn btn-square btn-outline btn-disable";

export default function ProductSale({
  id,
  productName,
  unitPrice,
  amount: limit,
  description,
  setShoppingCart,
}: ProductModel) {
  const [amount, setAmount] = useState(0);

  const plusButtonStyle = amount < limit ? normalStyle : disabledStyle;
  const minusButtonStyle = amount > 0 ? normalStyle : disabledStyle;

  useEffect(() => setAmount(0), [limit]);

  const addOneToAmount = () => {
    if (amount >= limit) {
      return;
    }
    setAmount((amount) => amount + 1);

    if (!amount && setShoppingCart) {
      setShoppingCart((shoppingCart: ProductModel[]) => [
        ...shoppingCart,
        {
          id,
          productName,
          unitPrice,
          amount: amount + 1,
          description,
          undefined,
        },
      ]);
    } else if (setShoppingCart) {
      setShoppingCart((shoppingCart: ProductModel[]) =>
        shoppingCart.map((product) =>
          product.id !== id ? product : { ...product, amount: amount + 1 }
        )
      );
    }
  };

  const decreaseOneToAmount = () => {
    if (amount <= 0) {
      return;
    }
    setAmount((amount) => amount - 1);

    if (amount === 1 && setShoppingCart) {
      setShoppingCart((shoppingCart: ProductModel[]) =>
        shoppingCart.filter((product) => product.id !== id)
      );
    } else if (setShoppingCart) {
      setShoppingCart((shoppingCart: ProductModel[]) =>
        shoppingCart.map((product) =>
          product.id !== id ? product : { ...product, amount: amount - 1 }
        )
      );
    }
  };

  return (
    <div
      className={`card shadow-lg ${
        amount !== 0 ? "bg-base-300" : "bg-base-100"
      }`}
    >
      <div className="card-body">
        <h2 className="card-title">{productName}</h2>
        <p>{description}</p>
        <div className="card-actions justify-between items-center">
          <p>Q. {unitPrice}</p>
          <div className="flex justify-between items-center">
            <button
              className={`${minusButtonStyle} text-2xl`}
              onClick={decreaseOneToAmount}
            >
              -
            </button>
            <button className={`${disabledStyle} m-2`}>{amount}</button>
            <button
              className={`${plusButtonStyle} text-2xl`}
              onClick={addOneToAmount}
            >
              +
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}
