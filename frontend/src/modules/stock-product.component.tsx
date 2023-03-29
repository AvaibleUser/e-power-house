import React, { useEffect } from "react";
import { useState } from "react";
import { useRevalidator } from "react-router-dom";

import ProductModel from "../models/product.model";
import { putStockFromBranch } from "../services/stock.service";

const normalStyle = "btn btn-square";
const disabledStyle = "btn btn-square btn-outline btn-disable";

export default function StockProduct({
  id,
  productName,
  amount: limit,
  description,
  unitPrice: branchId,
}: ProductModel) {
  const revalidator = useRevalidator();

  const [amount, setAmount] = useState(0);

  const plusButtonStyle = amount < limit ? normalStyle : disabledStyle;
  const minusButtonStyle = amount > 0 ? normalStyle : disabledStyle;

  useEffect(() => setAmount(0), [limit]);

  const addOneToAmount = () => {
    if (amount >= limit) {
      return;
    }
    setAmount((amount) => amount + 1);
  };

  const decreaseOneToAmount = () => {
    if (amount <= 0) {
      return;
    }
    setAmount((amount) => amount - 1);
  };

  const madeTransfer = async () => {
    if (amount === 0) {
      return;
    }
    try {
      putStockFromBranch({ branchId, productId: id, amount });
      revalidator.revalidate();
    } catch (e: unknown) {
      alert("Ocurrio un error al procesar la solicitud, intente mas tarde");
      console.error(e);
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
          <div className="flex justify-between items-center">
            <label
              htmlFor="sale-modal"
              className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg bg-accent text-neutral"
            >
              Realizar ingreso
            </label>
            <input type="checkbox" id="sale-modal" className="modal-toggle" />
            <div className="modal">
              <div className="modal-box relative">
                <label
                  htmlFor="sale-modal"
                  className="btn btn-sm btn-circle absolute right-2 top-2"
                >
                  ✕
                </label>
                <div className="flex flex-col items-center">
                  <h3 className="text-lg font-bold">
                    Cuantos '{productName}' va a ingresar
                  </h3>
                  <div className="flex w-3/4 justify-evenly items-center">
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
                  <button
                    className="btn btn-xs sm:btn-sm md:btn-md lg:btn-lg m-4"
                    onClick={madeTransfer}
                  >
                    Realizar ingreso
                  </button>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
