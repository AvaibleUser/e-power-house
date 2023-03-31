import React, { useState } from "react";
import JobTitle from "../../models/job-title.enum";
import Input from "../../modules/input.component";
import Select from "../../modules/select.component";
import { signUp } from "../../services/login.service";

const jobTitlesOptions = [
  "Vendedor",
  "Inventarista",
  "Bodeguero",
  "Administrador",
];

const branchIdOptions = ["Sucursal Central", "Sucursal Norte", "Sucursal Sur"];

const warehouseIdOptions = ["Bodega Central"];

export default function Employees() {
  const [cui, setCui] = useState("");
  const [name, setName] = useState("");
  const [lastName, setLastName] = useState("");
  const [password, setPassword] = useState("");
  const [branchId, setBranchId] = useState(-1);
  const [warehouseId, setWarehouse] = useState(-1);
  const [jobTitle, setJobTitle] = useState(-1);
  const [birthdate, setBirthdate] = useState(new Date("01/01/2023"));
  const [address, setAddress] = useState("");
  const [email, setEmail] = useState("");
  const [phone, setNumber] = useState("");

  const signUpEmployee = async () => {
    if (
      cui &&
      name &&
      lastName &&
      password &&
      +jobTitle > -1 &&
      ((+branchId > -1 && [0, 1].includes(+jobTitle)) ||
        (+warehouseId > -1 && +jobTitle === 2) ||
        +jobTitle === 3) &&
      birthdate &&
      address &&
      email &&
      phone
    ) {
      try {
        await signUp({
          cui,
          name,
          lastName,
          password,
          jobTitle,
          branchId: branchId + 1 || undefined,
          warehouseId: warehouseId + 1 || undefined,
          birthdate,
          address,
          email,
          phone,
        });

        alert("El empleado se registro con exito");
      } catch (e: unknown) {
        alert("No se pudo registrar al empleado, intentelo nuevamente");
      }
    } else {
      alert(
        "Los datos proporcionados no cumplen con los requerimientos, intentelo de nuevo"
      );
    }
  };

  const workPlace = [0, 1].includes(+jobTitle) ? (
    <Select
      label="Sucursal"
      placeholder="Escoja la sucursal"
      value={branchId}
      setValue={setBranchId}
      options={branchIdOptions}
    />
  ) : +jobTitle === 2 ? (
    <Select
      label="Bodega"
      placeholder="Escoja la bodega"
      value={warehouseId}
      setValue={setWarehouse}
      options={warehouseIdOptions}
    />
  ) : (
    <></>
  );

  return (
    <div className="bg-base-200 grid gap-4 grid-rows-2 w-full h-full p-4 md:px-10 lg:px-20">
      <Input
        label="CUI"
        type="text"
        placeholder="1234567980123"
        value={cui}
        setValue={setCui}
      />
      <Input
        label="Nombre"
        type="text"
        placeholder="nombre lorem"
        value={name}
        setValue={setName}
      />
      <Input
        label="Apellido"
        type="text"
        placeholder="ipsum apellido"
        value={lastName}
        setValue={setLastName}
      />
      <Input
        label="Contrase;a"
        type="password"
        placeholder="seguro123"
        value={password}
        setValue={setPassword}
      />
      <Select
        label="Puesto de trabajo"
        placeholder="Elija un puesto"
        value={jobTitle}
        setValue={setJobTitle}
        options={jobTitlesOptions}
      />
      {workPlace}
      <Input
        label="Fecha de nacimiento"
        type="date"
        placeholder="01/01/2023"
        value={birthdate}
        setValue={setBirthdate}
      />
      <Input
        label="Direccion"
        type="text"
        placeholder="Calle falsa 123"
        value={address}
        setValue={setAddress}
      />
      <Input
        label="Email"
        type="text"
        placeholder="lorem@ipsum.com"
        value={email}
        setValue={setEmail}
      />
      <Input
        label="Numero de telefono"
        type="text"
        placeholder="+502 1234-5678"
        value={phone}
        setValue={setNumber}
      />
      <button onClick={signUpEmployee} className="btn">
        Registrar empleado
      </button>
    </div>
  );
}
