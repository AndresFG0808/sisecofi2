export const useErrorMessages = (messages) => {
  const memoizedMessages = new Set(
    Object.values(messages).map((message) => message)
  );

  const getMessageExists = (message) => memoizedMessages.has(message);

  return { getMessageExists };
};
