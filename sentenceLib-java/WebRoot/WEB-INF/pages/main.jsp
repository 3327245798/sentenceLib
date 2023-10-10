<%@ page language="java" import="java.util.*" contentType="text/html; charset=utf-8"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<title>Webpack开发工具</title>
	<meta name="viewport" content="initial-scale=1, maximum-scale=1">
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<style>
		* {
			margin: 0;
			padding: 0;
			box-sizing: border-box;
		}
		html,body {
			display: flex;
			flex-direction: column;
			width: 100%;
			height: 100%;
			overflow: hidden;
		}
		iframe {
			width: 100%;
			height: 100%;
			flex: 1;
			display: none;
			background: white;
			border: 0;
		}
		p {
			display: none;
			border-bottom: 1px solid #ccc;
			padding: .5rem;
		}
		#toggle {
			position: fixed;
			display: inline-block;
			right: -2rem;
			top: -2rem;
			width: 4rem;
			height: 4rem;
			border-radius: 2rem;
			border: 0;
			opacity: .3;
		}
		#toggle:hover {
			background: #999;
			opacity: 1;
		}
	</style>
</head>
<body>
<p id="initInfo">
	请输入Webpack调试服务器的地址:
	<input type="text" value="http://127.0.0.1:8000/"/>
	<button onclick="gotoDebug(this)">请求调试</button>
</p>
<button id="toggle" onclick="toggleServerInput(this)"></button>
<iframe id="devIframe"></iframe>

<script type="text/javascript">
	window.g_runToolUrl = '${actionUrl}'; // 数据接口
	window.g_callToolUrl = '${runToolUrl}'; // 工具调用
	window.g_forwardUrl = '${forwardUrl}'; // 跳转接口
	window.g_resourceUrl = '${resourceUrl}'; // 静态资源
	window.g_userId = '${userID}'; // 当前用户账号
	window.g_accessToken = '${accessToken}'; // 当前用户的accessToken
	window.g_bandId = '${bandID}'; // 当前运行的帮区ID
	window.g_rtParam = '${toolParam}'; // 工具运行参数
	window.g_clientType = '${clientType}'; // 工具当前的运行平台
	window.g_thisToolId = '${toolID}'; // 工具的ID
	window.g_userAccount = '${userAccount}';
	window.g_userName = '${userName}';

	// 根据需要添加此项, 以把工具运行信息传递到webpack调试服务器中
	var ENV_KEYS = [
		'g_runToolUrl',
		'g_callToolUrl',
		'g_forwardUrl',
		'g_resourceUrl',
		'g_userId',
		'g_accessToken',
		'g_bandId',
		'g_rtParam',
		'g_clientType',
		'g_thisToolId',
		'g_userName',
		'g_userAccount'

	];

	function getEnvValues() {
		var result = {};
		for(var i = 0; i < ENV_KEYS.length; i ++) {
			var key = ENV_KEYS[i];
			var value = window[key];
			// 只保留项目根路径, 自动相对形成代理
			if(value.indexOf('http://127.0.0.1') == 0
					|| value.indexOf('http://localhost') == 0) {
				var matches = value.match(/http:\/\/.*?\//);
				if(matches.length > 0) {
					result[key] = value.replace(matches[0], '/');
				}
				continue;
			}
			// 如果是其它代理设置, 需要把源地址指向调试服务器, 请修改以下代码完成
			// if(value.startsWith('https://test.wetoband.com')) {
			// 	var matches = value.match(/https?:\/\/.*?\//);
			// 	if(matches.length > 0) {
			// 		result[key] = value.replace(matches[0], 'http://127.0.0.1:8000/');
			// 	}
			// 	continue;
			// }
			result[key] = value;
		}
		return result;
	}
	function gotoDebug() {
		var initInfo = document.getElementById('initInfo');
		var input = document.getElementsByTagName('input')[0];
		var iframe = document.getElementById('devIframe');
		iframe.src = input.value + '?env=' + encodeURIComponent(JSON.stringify(getEnvValues()));
		iframe.style.display = 'inline-block';
	}
	function toggleServerInput() {
		var initInfo = document.getElementById('initInfo');
		var display = initInfo.style.display;
		if(display == 'none') {
			initInfo.style.display = 'inline-block';
			return;
		}
		initInfo.style.display = 'none';
	}

	window.onload = function() {
		gotoDebug();
	}
</script>
</body>
</html>