import axios from "axios";
import Client from "../models/client.model";

const baseUrl = "http://localhost:8083"; //"http://sales:8080";

export async function getClient(nit: string): Promise<Client> {
  const { data } = await axios.get(`${baseUrl}/clients/${nit}`);

  return data;
}

export async function addClient(client: Client) {
  const { data } = await axios.post(`${baseUrl}/clients/`, client);

  return data;
}
