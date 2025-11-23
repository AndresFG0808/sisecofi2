import React from "react";
import LoadingOverlay from 'react-loading-overlay';
import { FadeLoader } from 'react-spinners'

const Loading = ({ active, children, text }) => {
  return (
    <LoadingOverlay
      active={active}
      text={text}
      // spinner={
      //   <FadeLoader   
      //     height={23}
      //     width={4}
      //     radius={1}
      //     margin={0}
      //     color={"#FFFFFF"}
      //   />
      // }
      spinner={
        text ? null : (
          <FadeLoader
            height={23}
            width={4}
            radius={1}
            margin={0}
            color={"#FFFFFF"}
          />
        )
      }
      fadeSpeed={0}

      style={{ overflow: 'hidden' }}

    // styles={{
    //   wrapper: {
    //     width: '100%',
    //     height: '100%',
    //     overflow: 'hidden'
    //   }
    // }}
    >
      {children}
    </LoadingOverlay>
  );
};

export default Loading;
