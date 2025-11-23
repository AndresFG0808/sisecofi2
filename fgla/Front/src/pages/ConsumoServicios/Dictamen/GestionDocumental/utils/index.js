import { nanoid } from "nanoid";
import {
  DownloadFileBase64,
  getMimeType,
} from "../../../../../functions/utils/base64.utils";

const regex = /\.[a-zA-Z0-9]+$/;
const isFase = /OTROS_DOCUMENTOS/;
const isFile = (path) => regex.test(path);
const isAddDocument = (descripcion = "") =>
  descripcion?.toLowerCase()?.trim()?.includes("otros documentos");
const isFaseType = (path) => isFase.test(path);

function addUUIDsAndClone(data) {
  // Recursive function to process each item
  function cloneAndAddUUIDs(items) {
    return items.map((item) => {
      // Clone the current item and add UUID
      const newItem = {
        ...item,
        UUID: nanoid(),
        // Recursively process `subRows` if it exists
        subRows:
          item.subRows && Array.isArray(item.subRows)
            ? cloneAndAddUUIDs(item.subRows)
            : [],
      };
      return newItem;
    });
  }

  // Start the cloning and UUID adding process
  return cloneAndAddUUIDs(data);
}
function extractSubRows(data) {
  let result = [];

  // Function to process each node
  function processNode(node) {
    // Add the current node to the result
    result.push(node);

    // If there are subRows, process them recursively
    if (node.subRows && Array.isArray(node.subRows)) {
      for (const subRow of node.subRows) {
        processNode(subRow);
      }
    }
  }

  // Start processing from the root
  for (const item of data) {
    processNode(item);
  }

  return result;
}
const getTotalDocuments = (data) => {
  let total = 0;
  let pending = 0;
  let loaded = 0;
  const allSubRows = extractSubRows(data);
  allSubRows.forEach((subRow) => {
    if (!subRow?.idCarpetaPlantillaProyecto && !subRow?.noAplica) {
      if (subRow?.cargado === true) {
        loaded++;
      } else if (subRow?.cargado === false) {
        pending++;
      }
    }
  });
  total = loaded + pending;
  return {
    todos: total,
    pendientes: pending,
    cargados: loaded,
  };
};

function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = structuredClone(bottomRow.getParentRow().original);
  updatedRow.subRows[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
function base64ToPdfBlobUrl(base64) {
  const byteCharacters = window.atob(base64);
  const byteArrays = [];

  const sliceSize = 512;
  for (let offset = 0; offset < byteCharacters.length; offset += sliceSize) {
    const slice = byteCharacters.slice(offset, offset + sliceSize);
    const byteNumbers = new Array(slice.length);

    for (let i = 0; i < slice.length; i++) {
      byteNumbers[i] = slice.charCodeAt(i);
    }

    const byteArray = new Uint8Array(byteNumbers);
    byteArrays.push(byteArray);
  }

  const blob = new Blob(byteArrays, { type: "application/pdf" });
  const blobUrl = URL.createObjectURL(blob);
  return blobUrl;
}
function getFilenameAndExtension(path) {
  const parts = path.split("/");
  const fileNameWithExtension = parts[parts.length - 1];
  const [fileName, extension] = fileNameWithExtension.split(".");
  return { fileName, extension };
}
function downloadFileFromPath(base64, path) {
  // Extract filename and extension
  const { fileName, extension } = getFilenameAndExtension(path);
  const mimeType = getMimeType(extension);

  // Construct filename with extension
  const filename = `${fileName}.${extension}`;

  // Call the download function
  DownloadFileBase64(base64, filename, mimeType);
}
export {
  addUUIDsAndClone,
  extractSubRows,
  getTotalDocuments,
  isFaseType,
  isFile,
  isAddDocument,
  updateRowFromSubRow,
  base64ToPdfBlobUrl,
  downloadFileFromPath,
};
