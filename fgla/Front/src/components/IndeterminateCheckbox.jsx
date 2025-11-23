import React, { forwardRef, useEffect, useRef } from "react"
import { Form } from "react-bootstrap"

const IndeterminateCheckbox = forwardRef(
    ({ indeterminate, ...rest }, ref) => {
        const defaultRef = useRef()
        const resolvedRef = ref || defaultRef

        useEffect(() => {
            resolvedRef.current.indeterminate = indeterminate
        }, [resolvedRef, indeterminate])

        return (
            <>
                <Form.Check type="checkbox" ref={resolvedRef} {...rest} />
            </>
        )
    }
)

export default IndeterminateCheckbox;