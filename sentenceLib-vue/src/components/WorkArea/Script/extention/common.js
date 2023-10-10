const onMentionSelected = (e, area, mention) => {
  if (!area.props.unit) {
    return;
  }
  e.target.blur();
  area.props.temp = [
    {
      class: mention.type,
      content: mention.name,
      id: mention.id,
    },
  ];
};
export { onMentionSelected };
