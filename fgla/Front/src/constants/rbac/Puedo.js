import rules from "./lasReglas";

const check = (rules, role, action, data) => {
    
  const permissions = rules[role];

  if (!permissions) {
       return false;
  }

  if (permissions && permissions.static[action]  && permissions.static[action].puedo) {
    return true;
  }

  const dynamicPermissions = permissions.dynamic;

  if (dynamicPermissions) {
    const permissionCondition = dynamicPermissions[action];
    if (!permissionCondition) {
      return false;
    }

    return permissionCondition(data);
  }
  return false;
};

const Puedo = props =>
  check(rules, props.role, props.perform, props.data)
    ? props.yes()
    : props.no();

Puedo.defaultProps = {
  yes: () => null,
  no: () => null
};

export default Puedo;