import React, { useRef, useState } from "react";
import styled from "styled-components";
import Portal from "./utils/Portal";

const StyledTooltip = styled.span.attrs((p) => ({
  bg: p.bg || "dark",
  delay: p.delay || 0.01,
}))`
  position: fixed;
  top: ${(p) => p.posRef.current.y}px;
  left: ${(p) => p.posRef.current.x}px;
  font-size: 0.6rem;
  font-weight: 600;
  letter-spacing: 0.02rem;
  background-color: rgba(0, 0, 0, 0.9);
  color: white;
  pointer-events: none;
  padding: 5px 7px;
  border-radius: 4px;
  z-index: 99999;
  display: inline-block;
  white-space: nowrap;
  opacity: ${(p) => p.show};

  transition-property: transform, opacity !important;
  transition-duration: 0.06s !important;
  transition-timing-function: cubic-bezier(0, 0, 0.2, 1) !important;
  transition-delay: ${(p) => (p.show ? p.delay : 0.02)}s !important;

  transform-origin: ${(p) => position(p.placement).negate()};
  transform: scale(${(p) => (p.show ? 1 : 0.7)});
`;
const position = (p) => ({
  current: p,
  negate() {
    if (this.current === "left") return "right";
    if (this.current === "right") return "left";
    if (this.current === "top") return "bottom";
    if (this.current === "bottom") return "top";
  },
  isHorizontal() {
    return this.current === "left" || this.current === "right";
  },
  isVertical() {
    return this.current === "top" || this.current === "bottom";
  },
});

const point = () => ({
  x: null,
  y: null,
  reset(p) {
    this.x = p.x;
    this.y = p.y;
  },
  restrictRect(rect) {
    if (this.x < rect.l) this.x = rect.l;
    else if (this.x > rect.r) this.x = rect.r;
    if (this.y < rect.t) this.y = rect.t;
    else if (this.y > rect.b) this.y = rect.b;
  },
});

const getPoint = (element, tooltip, placement, space) => {
  let recursionCount = 0;
  const pt = point();
  const boundaries = {
    l: space,
    t: space,
    r: document.body.clientWidth - (tooltip.clientWidth + space),
    b: window.innerHeight - (tooltip.clientHeight + space),
  };
  const box = element.getBoundingClientRect();

  return (function recursive(placement) {
    recursionCount++;
    const pos = position(placement);
    switch (pos.current) {
      case "left": {
        pt.x = box.left - (tooltip.offsetWidth + space);
        pt.y = box.top + (element.offsetHeight - tooltip.offsetHeight) / 2;
        break;
      }
      case "right": {
        pt.x = box.right + space;
        pt.y = box.top + (element.offsetHeight - tooltip.offsetHeight) / 2;
        break;
      }
      case "top": {
        pt.x = box.left + (element.offsetWidth - tooltip.offsetWidth) / 2;
        pt.y = box.top - (tooltip.offsetHeight + space);
        break;
      }
      default: {
        pt.x = box.left + (element.offsetWidth - tooltip.offsetWidth) / 2;
        pt.y = box.bottom + space;
        break;
      }
    }
    if (recursionCount < 3) {
      if (
        (pos.isHorizontal() && (pt.x < boundaries.l || pt.x > boundaries.r)) ||
        (pos.isVertical() && (pt.y < boundaries.t || pt.y > boundaries.b))
      ) {
        pt.reset(recursive(pos.negate()));
      }

      pt.restrictRect(boundaries);
    }
    return pt;
  })(placement);
};

export function Tooltip({
  text,
  placement = "bottom",
  space = 15,
  children,
  disabled = 0,
  delay,
  bg,
}) {
  const [show, setShow] = useState(0);
  const posRef = useRef({ x: 0, y: 0 });
  const tooltipRef = useRef();

  const handleMouseOver = (e) => {
    setShow(1);
    posRef.current = getPoint(
      e.currentTarget,
      tooltipRef.current,
      placement,
      space
    );
  };
  const handleMouseOut = () => setShow(0);
  return (
    <>
      {disabled
        ? children
        : React.cloneElement(children, {
            onMouseOver: handleMouseOver,
            onMouseOut: handleMouseOut,
          })}
      {disabled || (
        <Portal>
          <StyledTooltip
            delay={delay}
            bg={bg}
            ref={tooltipRef}
            posRef={posRef}
            show={show}
          >
            {text}
          </StyledTooltip>
        </Portal>
      )}
    </>
  );
}
