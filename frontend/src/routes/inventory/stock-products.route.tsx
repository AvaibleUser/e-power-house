import React, { useState } from "react";
import { useLoaderData, useRevalidator } from "react-router-dom";

import ProductModel from "../../models/product.model";
import StockProduct from "../../modules/stock-product.component";

export default function StockProducts() {
  const { branchId, data } = useLoaderData() as any;
  const stock = (data as ProductModel[]).filter((product) => product.amount);

  const products = stock.map(
    ({ id, productName, amount, description }) => (
      <StockProduct
        key={id}
        id={id}
        unitPrice={branchId as number}
        productName={productName}
        amount={amount}
        description={description}
      />
    )
  );

  return (
    <main className="bg-neutral py-8">
      <div className="flex justify-between items-center px-8"></div>
      <div className="grid bg-neutral p-8 gap-8 grid-cols-3">{products}</div>
    </main>
  );
}
