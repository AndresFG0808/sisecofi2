import { nanoid } from "nanoid";
import {
  DownloadFileBase64,
  validMimeTypes,
} from "../components/DownloadFile";

const regex = /\.[a-zA-Z0-9]+$/;
const isFase = /OTROS_DOCUMENTOS/;
const template = {
  descripcion: null,
  requerido: null,
  noAplica: null,
  estatus: null,
  justificacion: null,
  tamano: null,
  ultimaModificacion: null,
  acciones: null,
};
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

function updateRowFromSubRow(bottomRow, newRow) {
  if (!bottomRow.parentId) return newRow;
  const newBottomRow = bottomRow.getParentRow();
  const updatedRow = structuredClone(bottomRow.getParentRow().original);
  updatedRow.subRows[bottomRow.index] = newRow;
  return updateRowFromSubRow(newBottomRow, updatedRow);
}
const isFile = (path) => regex.test(path);
const isAddDocument = (descripcion = "") =>
  descripcion?.toLowerCase()?.trim()?.includes("otros documentos");
const isFaseType = (path) => isFase.test(path);

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
function addUUIDs(data) {
  data.forEach((item) => {
    // Add UUID to the current item
    item.id = nanoid();

    // Process `subRows` recursively
    if (item.subRows && Array.isArray(item.subRows)) {
      addUUIDs(item.subRows);
    }
  });
}

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
const getTotalDocuments = (data) => {
  let total = 0;
  let pending = 0;
  let loaded = 0;
  const allSubRows = extractSubRows(data);
  allSubRows.forEach((subRow) => {
    if (!subRow?.idCarpetaPlantillaProyecto) {
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

function getFilenameAndExtension(path) {
  const parts = path.split("/");
  const fileNameWithExtension = parts[parts.length - 1];
  const [fileName, extension] = fileNameWithExtension.split(".");
  return { fileName, extension };
}
function getMimeType(extension) {
  return validMimeTypes[extension] || "application/octet-stream"; // Default MIME type if not found
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
  base64ToPdfBlobUrl,
  updateRowFromSubRow,
  getTotalDocuments,
  isFile,
  isAddDocument,
  isFaseType,
  extractSubRows,
  addUUIDsAndClone,
  downloadFileFromPath,
};
