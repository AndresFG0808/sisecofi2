import withLazyLoading from '../HOCs/withLazyLoading';

const FileField = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-FileField" */ './FileField'));
const FilterField = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-FilterField" */ './FilterField'));
const FormCheck = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-FormCheck" */ './FormCheck'));
const RadioButtonHabilDes = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-RadioButtonHabilDes" */ './RadioButtonHabilDes'));
const RadioButtonSiNo = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-RadioButtonSiNo" */ './RadioButtonSiNo'));
const Select = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-Select" */ './Select'));
const SelectMultiple = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-SelectMultiple" */ './SelectMultiple'));
const SelectSingle = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-SelectSingle" */ './SelectSingle'));
const TextArea = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TextArea" */ './TextArea'));
const TextField = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TextField" */ './TextField'));
const TextFieldDate = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TextFieldDate" */ './TextFieldDate'));
const TextFieldIcon = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TextFieldIcon" */ './TextFieldIcon'));
const TextFieldNumber = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-TextFieldNumber" */ './TextFieldNumber'));
const FormCheckLabel = withLazyLoading(() => import(/* webpackChunkName: "LazyComponent-FormCheckLabel" */ './FormCheckLabel'));

export {
    FileField,
    FilterField,
    FormCheck,
    RadioButtonHabilDes,
    RadioButtonSiNo,
    Select,
    SelectMultiple,
    SelectSingle,
    TextArea,
    TextField,
    TextFieldDate,
    TextFieldIcon,
    TextFieldNumber,
    FormCheckLabel
  };