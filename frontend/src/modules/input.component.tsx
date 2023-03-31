import React from "react";

export default function Input({
  label,
  placeholder,
  value,
  setValue,
  type = "text",
}: any) {
  return (
    <div className="form-control">
      <label className="input-group input-group-vertical">
        <span>{label}</span>
        <input
          onChange={(event) => setValue(event.target.value)}
          value={value}
          type={type}
          placeholder={placeholder}
          className="input input-bordered"
        />
      </label>
    </div>
  );
}
