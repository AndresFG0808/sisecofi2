import React, { lazy, Suspense } from 'react';
import Loader from '../Loader';

const withLazyLoading = (importFunc) => {
    const LazyComponent = lazy(importFunc);

    return (props) => (
        <Suspense fallback={<Loader />}>
            <LazyComponent {...props} />
        </Suspense>
    );
};

export default withLazyLoading;