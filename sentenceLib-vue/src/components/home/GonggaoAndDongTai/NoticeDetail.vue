<template>
  <a-layout>
    <a-layout-header :style="{ background: '#fcfcfc' }">
      <div class="logo" :style="{ background: '#fcfcfc',width:'100px' }"></div><!-- 这一行怎么不起作用 -->
      <a-menu
        mode="horizontal"
        :default-selected-keys="['2']"
        :style="{ linHeight: '64px', background: '#fcfcfc' }"
      >
        <a-sub-menu key="sub1">
          <span slot="title"><a-icon type="user" />公告</span>
          <a-menu-item key="1">信息公开</a-menu-item>
          <a-menu-item key="2">服务</a-menu-item>
          <a-menu-item key="3">互动</a-menu-item>
        </a-sub-menu>
        <a-sub-menu key="sub2">
          <span slot="title"><a-icon type="laptop" />数据说明</span>
          <a-menu-item key="4">概况</a-menu-item>
          <a-menu-item key="5">网上办事</a-menu-item>
          <a-menu-item key="6">解读回应</a-menu-item>
        </a-sub-menu>
        <!--  <a-menu-item key="1">nav 1</a-menu-item>
        <a-menu-item key="2">nav 2</a-menu-item>
        <a-menu-item key="3">nav 3</a-menu-item> -->
      </a-menu>
    </a-layout-header>
    <a-layout>
      <a-layout-sider :style="{ background: '#f5f5f5', padding: '5px' ,width:'500px'}">
        <a-layout-content
          :style="{
            background: '#009999',
            height: '50px',
            color: '#fff',
            'font-size': '25px',
            'text-align': 'center',
            'padding-top': '13px',
            'margin-bottom': '10px',
          }"
        >
          最新发布
        </a-layout-content>
        <a-layout-content
          :style="{
            color: '#996b1f',
            'font-size': '2px',
            'padding-top': '13px',
          }"
        >
          <ul>
            <li :style="{background:'#eefff0','margin-bottom':'10px'}">
              05-22 &nbsp;&nbsp;
              <a
                @click="$router.push('noticeDetail')"
                :style="{ 
                //'white-space': 'nowrap',
                'overflow':'hidden',
                'text-overflow': 'ellipsis',
                
                 color: '#463900' }"
              >
                关于举行宋晓龙学术报告的通知
                </a>
            </li>
            <li :style="{background:'#eefff0','margin-bottom':'10px'}">
              05-21 &nbsp;&nbsp;
              <a
                @click="$router.push('noticeDetail')"
                :style="{
                  overflow: 'hidden',
                  'text-overflow': 'ellipsis',
                  color: '#463900',
                }"
                >关于元旦放假的通知</a
              >
            </li>
            <li :style="{background:'#eefff0','margin-bottom':'10px'}">
              05-18 &nbsp;&nbsp;
              <a
                @click="$router.push('noticeDetail')"
                :style="{
                  overflow: 'hidden',
                  'text-overflow': 'ellipsis',
                  color: '#463900',
                }"
                >关于工厂客服</a
              >
            </li>
            <li></li>
          </ul>
        </a-layout-content>
        <!--      <a-menu
          
          mode="inline"
          :default-selected-keys="['1']"
          :default-open-keys="['sub1']"
          :style="{ height: '100%', borderRight: 0 }"
        >
          <a-sub-menu key="sub1">
            <span slot="title"><a-icon type="user" />subnav1</span>
            <a-menu-item key="1">选项1</a-menu-item>
            <a-menu-item key="2">选项2</a-menu-item>
            <a-menu-item key="3">选项3</a-menu-item>
          </a-sub-menu>
          <a-sub-menu key="sub2">
            <span slot="title"><a-icon type="laptop" />subnav2</span>
            <a-menu-item key="4">选项4</a-menu-item>
            <a-menu-item key="5">选项5</a-menu-item>
            <a-menu-item key="6">选项6</a-menu-item>
          </a-sub-menu>
        </a-menu> -->
      </a-layout-sider>
      <a-layout :style="{ width: '45%', padding: '0 24px 24px' }">
        <a-breadcrumb style="margin: 16px 0" separator=">">
          <a-breadcrumb-item>主页</a-breadcrumb-item>
          <a-breadcrumb-item>公告</a-breadcrumb-item>
          <a-breadcrumb-item>正文</a-breadcrumb-item>
        </a-breadcrumb>
        <a-layout-content
          :style="{
            background: '#fff',
            padding: '24px',
            margin: 0,
            minHeight: '280px',
          }"
        >
          <div>
            <h1 style="text-align: center">关于举行宋晓龙先生学术报告的通知</h1>
            <a-divider style="height: 2px; background-color: #d3d3d3" />
            <p style="fontsize: 2px; text-align: center">
              2022年5月22日 浏览次数:153
            </p>
            <a-divider style="background-color: #d3d3d3" />
            <p>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;软件学院邀请到粤港澳大湾区国家科技创新中心-
              工业软件产业发展中心主任宋孝龙先生到我院作“工业软件产业领域的发展与创新”学术报告。
            </p>
            <br />
            <p>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;时间：2022年4月7日下午14：00-15：30
            </p>
            <br />
            <p>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;地点：广州大学城软件学院B8附楼-207学术报告厅
            </p>
            <br />
            <p>
              &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;工业软件对国家工业现代化和现代制造业的升级至关重要，提升国产工业软件的实力与地位
              ，把握工业软件的核心命脉，在工业4.0的大舞台上占据一席之地、乃至引领工业软件产业的发展显得尤为关键！
            </p>
            <br />
          </div>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-layout>
</template>
<script>
//<!-- 本页面是每个公告的详情页面 -->
</script>
<style lang="scss">
</style>
