import React, { memo, useCallback, } from 'react';
import isEqual from 'lodash/isEqual';

const SelectWrapper = ({ options, value, type }) => {

  // Memoizar la función getTextById
  const getTextById = useCallback((options, id) => {
    const element = options.find((item) => item.id === id);
    return element ? element.texto : "";
  }, []);
  return <>{getTextById(options, value)}</>;
};

const deepCompare = (prevProps, nextProps) => {
    return (
      prevProps.value === nextProps.value &&
      isEqual(prevProps.options, nextProps.options) &&
      prevProps.type === nextProps.type
    );
  };

export default memo(SelectWrapper, deepCompare);