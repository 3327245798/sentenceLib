import Vue from "vue";
import {
  Avatar,
  Alert,
  Affix,
  Anchor,
  Badge,
  Button,
  Breadcrumb,
  Cascader,
  Card,
  Carousel,
  Checkbox,
  Col,
  Collapse,
  DatePicker,
  Descriptions,
  Divider,
  Drawer,
  Dropdown,
  Empty,
  Form,
  FormModel,
  Input,
  Icon,
  Layout,
  List,
  Menu,
  message,
  Modal,
  PageHeader,
  Popconfirm,
  Popover,
  Radio,
  Result,
  Row,
  Select,
  Spin,
  Table,
  Tabs,
  Tag,
  Timeline,
  Tree,
  Upload,
  Tooltip,
 
} from "ant-design-vue";

const { Content, Header, Footer, Sider } = Layout;
const { RangePicker, MonthPicker, WeekPicker } = DatePicker;

const BreadcrumbItem = Breadcrumb.Item;
const BreadcrumbSeparator = Breadcrumb.Separator;
const CollapsePanel = Collapse.Panel;
const DescriptionsItem = Descriptions.Item;
const FormItem = Form.Item;
const FormModelItem = FormModel.Item;
const InputGroup = Input.Group;
const InputSearch = Input.Search;
const InputTextArea = Input.TextArea;
const MenuItem = Menu.Item;
const MenuSubMenu = Menu.SubMenu;
const MenuItemGroup = Menu.ItemGroup;
const RadioGroup = Radio.Group;
const RadioButton = Radio.Button;
const SelectOption = Select.Option;
const TabPane = Tabs.TabPane;
const TimelineItem = Timeline.Item;
const ListItem = List.Item;
const ListItemMeta = ListItem.Meta;

Vue.component(Avatar.name, Avatar);
Vue.component(Affix.name, Affix);
Vue.component(Alert.name, Alert);
Vue.component( Anchor.name,  Anchor);
Vue.component(Badge.name, Badge);
Vue.component(Breadcrumb.name, Breadcrumb);
Vue.component(BreadcrumbItem.name, BreadcrumbItem);
Vue.component(BreadcrumbSeparator.name, BreadcrumbSeparator);
Vue.component(Button.name, Button);
Vue.component(Checkbox.name, Checkbox);
Vue.component(Cascader.name, Cascader);
Vue.component(Card.name, Card);
Vue.component(Carousel.name, Carousel);
Vue.component(Col.name, Col);
Vue.component(Content.name, Content);
Vue.component(Collapse.name, Collapse);
Vue.component(CollapsePanel.name, CollapsePanel);
Vue.component(DatePicker.name, DatePicker);
Vue.component(Descriptions.name, Descriptions);
Vue.component(DescriptionsItem.name, DescriptionsItem);
Vue.component(Divider.name, Divider);
Vue.component(Drawer.name, Drawer);
Vue.component(Dropdown.name, Dropdown);
Vue.component(Empty.name, Empty);
Vue.component(Footer.name, Footer);
Vue.component(Form.name, Form);
Vue.component(FormItem.name, FormItem);
Vue.component(FormModel.name, FormModel);
Vue.component(FormModelItem.name, FormModelItem);
Vue.component(Header.name, Header);
Vue.component(Icon.name, Icon);
Vue.component(Input.name, Input);
Vue.component(InputGroup.name, InputGroup);
Vue.component(InputSearch.name, InputSearch);
Vue.component(InputTextArea.name, InputTextArea);
Vue.component(List.name, List);
Vue.component(ListItem.name, ListItem);
Vue.component(ListItemMeta.name, ListItemMeta);
Vue.component(Layout.name, Layout);
Vue.component(Menu.name, Menu);
Vue.component(MenuItem.name, MenuItem);
Vue.component(MenuSubMenu.name, MenuSubMenu);
Vue.component(MenuItemGroup.name, MenuItemGroup);
Vue.component(Modal.name, Modal);
Vue.component(MonthPicker.name, MonthPicker);
Vue.component(PageHeader.name, PageHeader);
Vue.component(Popconfirm.name, Popconfirm);
Vue.component(Popover.name, Popover);
Vue.component(Radio.name, Radio);
Vue.component(RadioGroup.name, RadioGroup);
Vue.component(RadioButton.name, RadioButton);
Vue.component(RangePicker.name, RangePicker);
Vue.component(Result.name, Result);
Vue.component(Row.name, Row);
Vue.component(Spin.name, Spin);
Vue.component(Sider.name, Sider);
Vue.component(Select.name, Select);
Vue.component(SelectOption.name, SelectOption);
Vue.component(Table.name, Table);
Vue.component(Tabs.name, Tabs);
Vue.component(TabPane.name, TabPane);
Vue.component(Tag.name, Tag);
Vue.component(Timeline.name, Timeline);
Vue.component(TimelineItem.name, TimelineItem);
Vue.component(Tree.name, Tree);
Vue.component(Tooltip.name, Tooltip);
Vue.component(Upload.name, Upload);
Vue.component(WeekPicker.name, WeekPicker);

Vue.use(Modal);

Vue.prototype.$info = Modal.info;
Vue.prototype.$success = Modal.success;
Vue.prototype.$error = Modal.error;
Vue.prototype.$warning = Modal.warning;
Vue.prototype.$confirm = Modal.confirm;
Vue.prototype.$message = message;

import * as MathLive from "mathlive";
import MathfieldComponent from "./vue-mathlive.js";
Vue.use(MathfieldComponent, MathLive);
