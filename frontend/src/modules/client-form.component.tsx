import React, { useState } from "react";
import Input from "./input.component";

export default function ClientForm({ nit, setNit, completeSale }: any) {
  const [showAll, setShowAll] = useState(false);
  const [name, setName] = useState("");
  const [lastName, setLastName] = useState("");
  const [address, setAddress] = useState("");
  const [phone, setPhone] = useState("");
  const [birthdate, setBirthdate] = useState(new Date());

  if (nit.length < 13 && showAll) {
    setShowAll(false);
  }

  const restOfInputs = (
    <>
      <Input
        label="Nombre"
        placeholder="nombre lorem"
        value={name}
        setValue={setName}
      />
      <Input
        label="Apellido"
        placeholder="ipsum apellido"
        value={lastName}
        setValue={setLastName}
      />
      <Input
        label="Direccion"
        placeholder="Calle falsa 123"
        value={address}
        setValue={setAddress}
      />
      <Input
        label="Telefono"
        placeholder="+502 123987456"
        value={phone}
        setValue={setPhone}
      />
      <Input
        label="Fecha de nacimiento"
        type="date"
        placeholder="01/01/2023"
        value={birthdate}
        setValue={setBirthdate}
      />
    </>
  );

  return (
    <div className="grid p-4 gap-4 grid-rows-2">
      <Input
        label="Nit"
        placeholder="1234567890123"
        value={nit}
        setValue={setNit}
      />
      {showAll ? restOfInputs : <></>}
      <button
        onClick={() => {
          setShowAll(true);
          completeSale(showAll, name, lastName, address, phone, birthdate);
        }}
        className="btn"
      >
        Realizar compra
      </button>
    </div>
  );
}
