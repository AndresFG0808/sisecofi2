import { nanoid } from "nanoid";

export const useEditableTable = (
  data = [],
  options = { hasSubRows: false, template: {} }
) => {
  const [dataTable, setDataTable] = useState([]);
  const [memoizedData, setMemoizedData] = useState(new Map());

  const onAddNewRow = () => {
    setDataTable((prev) => [...prev, { ...template }]);
  };
  const onInitializeTable = () => {};
  const onEditRow = () => {};
  const onDeleteRow = () => {};
  // on add subrow
  // get modified rows
  // get new rows

  return {
    dataTable,
    memoizedData,
    setDataTable,
    setMemoizedData,
  };
};

function addUUIDsAndClone(data) {
  // Recursive function to process each item
  function cloneAndAddUUIDs(items) {
    return items.map((item) => {
      // Clone the current item and add UUID
      const newItem = {
        ...item,
        UUID: nanoid(),
        // Conditionally process `subRows` if it exists and is an array
        ...(item.subRows && Array.isArray(item.subRows)
          ? {
              subRows: cloneAndAddUUIDs(item.subRows),
            }
          : {}),
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
