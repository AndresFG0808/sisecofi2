import React, { memo, useCallback, } from 'react';
import isEqual from 'lodash/isEqual';

const SelectWrapper = ({ options, value, keyProp, name }) => {
  const getTextById = useCallback((options, id) => {
    const element = options.find((item) => item[keyProp] === id);
    return element ? element[name] : "";
  }, []);
  return <>{getTextById(options, value)}</>;
};

const deepCompare = (prevProps, nextProps) => {
    return (
      prevProps.value === nextProps.value &&
      isEqual(prevProps.options, nextProps.options) &&
      prevProps.keyProp === nextProps.keyProp &&
      prevProps.name === nextProps.name
    );
  };

export default memo(SelectWrapper, deepCompare);
