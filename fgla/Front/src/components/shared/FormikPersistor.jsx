import React, { PureComponent } from 'react'
import { FormikConsumer } from 'formik'

type StringHashMapType = { [key: string]: string }

type FormikPersistorProps = {
  name: string,
  values: StringHashMapType,
  errors: StringHashMapType,
  setValues: (values: StringHashMapType) => void,
  setErrors: (errors: StringHashMapType) => void,
}

class FormikPersistor extends PureComponent<FormikPersistorProps> {
  componentWillMount() {
    window.addEventListener('beforeunload', this.clear)
  }

  componentDidMount() {
    const { setValues, setErrors } = this.props
    const data = sessionStorage.getItem(this.storageKey)
    if (data) {
      const { values, errors } = JSON.parse(data)
      setValues(values)
      setErrors(errors)
    }
  }

  componentDidUpdate() {
    const { values, errors } = this.props
    sessionStorage.setItem(this.storageKey, JSON.stringify({ values, errors }))
  }

  componentWillUnmount() {
    window.removeEventListener('beforeunload', this.clear)
  }

  get storageKey() {
    return `formik.form.${this.props.name}`
  }

  clear = () => {
    sessionStorage.removeItem(this.storageKey)
  }

  props: FormikPersistorProps

  render() {
    return null
  }
}

const FormikPersist = ({ name }: { name: string }) => (
  <FormikConsumer>
    {({ values, errors, setValues, setErrors }) => (
      <FormikPersistor
        name={name}
        setValues={setValues}
        setErrors={setErrors}
        values={values}
        errors={errors}
      />
    )}
  </FormikConsumer>
)

export default FormikPersist;