import React from "react";

export default function Select({
  label,
  placeholder,
  value,
  setValue,
  options,
}: any) {
  return (
    <label className="input-group input-group-vertical max-w-full">
      <span>{label}</span>
      <select
        value={value}
        onChange={(event) => setValue(event.target.value)}
        className="select select-bordered w-full max-w-full"
      >
        <option disabled value={-1}>
          {placeholder}
        </option>
        {options.map((option: string, index: number) => (
          <option value={index} key={option}>
            {option}
          </option>
        ))}
      </select>
    </label>
  );
}
