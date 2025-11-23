import createIdbStorage from "redux-persist-indexeddb-storage";
import storageSession from "redux-persist/lib/storage/session";
import CryptoJS from "crypto-js";
const secretKey = "123456@TL.!";

export const createStorage = () =>
  window.indexedDB
    ? createIdbStorage({ name: "SAT", storeName: "root" })
    : storageSession;
export const encrypt = (text) =>
  CryptoJS.AES.encrypt(JSON.stringify(text), secretKey).toString();
export const decrypt = (text) => {
  const bytes = CryptoJS.AES.decrypt(text, secretKey);
  return JSON.parse(bytes.toString(CryptoJS.enc.Utf8));
};
