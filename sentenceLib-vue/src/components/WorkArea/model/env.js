function getUserEnv(envs) {
  const userEnv = [];
  envs.forEach((env) => {
    env.bandUsers.forEach((item) => {
      userEnv.push({ id: item.userId, name: item.userName, type: "user", label: "用户" });
    });
  });
  return userEnv;
}

function getRoleEnv(envs) {
  const roleEnv = [];
  envs.forEach((env) => {
    env.bandRoles.forEach((item) => {
      roleEnv.push({ id: item.roleId, name: item.roleName, type: "role", label: "角色" });
    });
  });
  return roleEnv;
}

function getBandEnv(envs) {
  const bandEnv = [];
  envs.forEach((env) => {
    bandEnv.push({
      id: env.bandInfo[0].bandId,
      name: env.bandInfo[0].name,
      type: "band",
      label: "帮区",
    });
  });
  return bandEnv;
}

function getToolEnv(envs) {
  const toolEnv = [];
  envs.forEach((env) => {
    env.bandTools.forEach((item) => {
      toolEnv.push({ id: item.toolId, name: item.toolName, type: "tool", label: "工具", runTool: item.runTool });
    });
  });
  return toolEnv;
}

function getActionEnv(envs) {
  const actionEnv = [];
  envs.forEach((env) => {
    env.toolActions.forEach((item) => {
      actionEnv.push({
        id: item.actionId,
        name: item.actionName,
        type: "action",
        label: "部件",
        params: item.params.map((param) => ({ ...param, value: "", temp: "" })),
      });
    });
  });
  return actionEnv;
}

function getChatroomEnv(envs) {
  const actionEnv = [];
  envs.forEach((env) => {
    env.bandChatrooms.forEach((item) => {
      actionEnv.push({
        id: item.chatroomId,
        name: item.chatroomName,
        type: "chatroom",
        label: "消息板",
      });
    });
  });
  return actionEnv;
}

function getScriptTemplateEnv(scriptTemplates) {
  const scriptTemplateEnv = [];
  scriptTemplateEnv.push(
    ...scriptTemplates.map((st) => ({
      id: st.id,
      name: st.description,
      content: st.content,
      example: st.example,
      type: "scriptTemplate",
      label: "句型",
    }))
  );
  return scriptTemplateEnv;
}

function addEnvs(types, envs, scriptTemplates) {
  const result = [];
  if (types.indexOf("user") !== -1) {
    result.push(...getUserEnv(envs));
  }
  if (types.indexOf("role") !== -1) {
    result.push(...getRoleEnv(envs));
  }
  if (types.indexOf("band") !== -1) {
    result.push(...getBandEnv(envs));
  }
  if (types.indexOf("tool") !== -1) {
    result.push(...getToolEnv(envs));
  }
  if (types.indexOf("action") !== -1) {
    result.push(...getActionEnv(envs));
  }
  if (types.indexOf("chatroom") !== -1) {
    result.push(...getChatroomEnv(envs));
  }
  if (types.indexOf("scriptTemplate") !== -1) {
    result.push(...getScriptTemplateEnv(scriptTemplates));
  }

  return result;
}

export function customEnvs(types, input, { envs, scriptTemplates }) {
  let result = addEnvs(types, envs, scriptTemplates);
  if (input !== "") result = result.filter((item) => item.name.indexOf(input) !== -1);
  return result;
}
