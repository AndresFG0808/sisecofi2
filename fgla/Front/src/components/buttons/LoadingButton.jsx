import React from "react";
import { Button, Spinner } from "react-bootstrap";

const LoadingButton = ({
    children,
    className,
    onClick,
    disabled,
    loading,
    type,
    variant
}) => {
    return (
        <Button
            variant={variant}
            className={className}
            onClick={onClick}
            disabled={disabled || loading}
            type={type}
        >
            {
                loading &&
                <>
                    <Spinner
                        as="span"
                        animation="border"
                        variant="dark"
                        size="sm"
                        role="status"
                        aria-hidden="true"
                    />
                    &nbsp;&nbsp;
                </>
            }
            {children}
        </Button>
    );
};

LoadingButton.defaultProps = {
    variant: "primary",
    className: "btn-sm ms-2 waves-effect waves-light"
  };

export default LoadingButton;
