import React from "react";
import { useState } from "react";
import { useRevalidator, useLoaderData } from "react-router-dom";

import ProductModel from "../../models/product.model";
import ClientForm from "../../modules/client-form.component";
import ProductSale from "../../modules/product-sale.component";
import { addClient, getClient } from "../../services/client.service";
import { addSale } from "../../services/sale.service";

export default function Sales() {
  const revalidator = useRevalidator();
  const stock = (useLoaderData() as ProductModel[]).filter(
    (product) => product.amount
  );

  const [shoppingCart, setShoppingCart] = useState<ProductModel[]>([]);
  const [nit, setNit] = useState("");
  const [modalTitle, setModalTitle] = useState("Casi terminamos la compra");
  const [modalMsg, setModalMsg] = useState(
    "Solo se necesitan los datos del cliente"
  );

  const {
    total: totalProducts,
    differents: totalDifferentProducts,
    price: totalPrice,
  } = shoppingCart.reduce(
    ({ total, differents, price }, product) => ({
      total: product.amount + total,
      differents: differents + 1,
      price: price + product.amount * product.unitPrice,
    }),
    { total: 0, differents: 0, price: 0 }
  );

  const completeSale = async (
    showAll: boolean,
    name: string,
    lastName: string,
    address: string,
    phone: string,
    birthdate: Date
  ) => {
    if ((nit.length === 13 || nit === "CF") && !showAll) {
      try {
        const client = await getClient(nit);

        try {
          await addSale({
            id: 0,
            clientNit: client.nit,
            saleDetails: shoppingCart.map((product) => ({
              saleId: 0,
              productId: product.id,
              amount: product.amount,
              unitPrice: product.unitPrice,
            })),
          });

          setNit("");
          setModalTitle("La venta se registro con exito");
          setModalMsg("");
          setTimeout(() => {
            stock.length = 0;
            setShoppingCart([]);
            revalidator.revalidate();
            setModalTitle("Casi terminamos la compra");
            setModalMsg("Solo se necesitan los datos del cliente");
          }, 2500);
        } catch (e: unknown) {
          setNit("");
        }
      } catch (e: unknown) {
        setModalMsg(
          "El nit no se encontra registrado, llene el siguiente formulario para registrarlo"
        );
        return;
      }
    } else if (showAll) {
      if (name && lastName && address && phone && birthdate) {
        try {
          await addClient({
            nit,
            name,
            lastName,
            address,
            phone,
            birthdate,
          });
          completeSale(!showAll, "", "", "", "", new Date());
        } catch (e: unknown) {
          setModalMsg(
            "Hubo un error en los datos, verifique que sean correctos"
          );
          setNit("");
        }
      } else {
        setModalMsg("Debe de llenar todos los campos");
      }
    } else {
      setNit(nit);
    }
  };

  const products = stock.map(
    ({ id, productName, unitPrice, amount, description }) => (
      <ProductSale
        key={id}
        id={id}
        productName={productName}
        unitPrice={unitPrice}
        amount={amount}
        description={description}
        setShoppingCart={setShoppingCart}
      />
    )
  );

  return (
    <main className="bg-neutral py-8">
      <div className="flex justify-between items-center px-8">
        <div className="stats shadow">
          <div className="stat flex items-center">
            <div className="stat-title">Productos diferentes</div>
            <div className="stat-value">{totalDifferentProducts}</div>
          </div>

          <div className="stat flex items-center">
            <div className="stat-title">Total de productos</div>
            <div className="stat-value">{totalProducts}</div>
          </div>

          <div className="stat flex items-center">
            <div className="stat-title">Costo total</div>
            <div className="stat-value">Q. {totalPrice.toFixed(2)}</div>
          </div>
        </div>
        <label
          htmlFor="sale-modal"
          className="btn btn-wide bg-accent text-neutral"
        >
          Finalizar compra
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
            <h3 className="text-lg font-bold">
              {shoppingCart.length
                ? modalTitle
                : "No hay productos en el carrito"}
            </h3>
            <p className="py-4">
              {shoppingCart.length
                ? modalMsg
                : "Para realizar la compra se debe de utilizar el boton con el `+` en al menos un producto"}
            </p>
            {shoppingCart.length && modalMsg.length > 0 ? (
              <ClientForm
                nit={nit}
                setNit={setNit}
                completeSale={completeSale}
              />
            ) : (
              <></>
            )}
          </div>
        </div>
      </div>
      <div className="grid bg-neutral p-8 gap-8 grid-cols-3">{products}</div>
    </main>
  );
}
