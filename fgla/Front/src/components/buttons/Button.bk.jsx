import React from 'react';
import Button from 'react-bootstrap/Button';
import styled from 'styled-components';
import { variant } from 'styled-system';

const ButtonVariants = styled(Button)(
  {
    appearance: 'none',
    fontFamily: 'inherit',
    textTransform: 'none',
    fontSize: '13px',
    boxShadow: 'none',
    // padding: '0.6rem 1.7rem',
    // margin: '0px',
  },
  variant({
    prop: 'color',
    variants: {
      secondary: {
        // bg: 'secondary',
        backgroundColor: '#fff',
        color: '#424242',
        border: '1px solid #cccccc',
        borderRadius: '4px',
        '&:hover': {
          backgroundColor: '#f6f6f6',
          color: '#424242',
        },
      },
      primary: {
        // bg: 'primary',
        backgroundColor: '#b0c4de',
        color: '#000000',
        border: '1px solid #b0c4de',
        '&:hover': {
          backgroundColor: '#6986ac',
          color: '#fff',
          border: '1px solid #6986ac',
        },
      },
    },
  }),
);

export default ButtonVariants;
