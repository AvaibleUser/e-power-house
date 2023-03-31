import React, { useState } from "react";

import reports from "../../jsons/reports.json";

export default function Reports() {
  const [actualReport, setActualReport] = useState(<></>);

  const changeReport = async (
    reportName: string,
    headers: string[],
    attributes: string[],
    dataLoader: Function
  ) => {
    const reportData = (await dataLoader()) as any[];

    setActualReport(
      <>
        <thead>
          <tr>
            {headers.map((header: string) => (
              <th key={header}>{header}</th>
            ))}
          </tr>
        </thead>
        <tbody>
          {reportData.map((dataRow, index) => (
            <tr key={`${reportName}-${index}`}>
              <td>{index + 1}</td>
              {attributes.map((attribute) => (
                <td id={`${reportName}-${attribute}`}>
                  {dataRow[attribute] !== undefined ? dataRow[attribute] : ""}
                </td>
              ))}
            </tr>
          ))}
        </tbody>
      </>
    );
  };

  const reportsFormatted = (
    <nav className="menu bg-base-100 w-56 rounded-box mx-4">
      {reports.map(({ category, reports }) => (
        <ul key={category}>
          <li className="menu-title">
            <span>{category}</span>
          </li>
          {reports.map(
            ({ headers, attributes, dataLoader, reportName }, index) => (
              <li key={`${index}`}>
                <span
                  onClick={() => {
                    changeReport(reportName, headers, attributes, dataLoader);
                  }}
                >
                  {reportName}
                </span>
              </li>
            )
          )}
        </ul>
      ))}
    </nav>
  );

  return (
    <div className="flex-auto bg-base-300 flex items-start py-4">
      {reportsFormatted}
      <div className="divider md:divider-horizontal" />
      <div className="overflow-x-auto overflow-y-auto h-full mx-4">
        <table className="table w-full">{actualReport}</table>
      </div>
    </div>
  );
}
