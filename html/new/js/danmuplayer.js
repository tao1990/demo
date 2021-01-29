
/*!
 *弹幕引擎核心
 *
 * Copyright 2015 by Liyawei Of AcGit.cc 
 * @license MIT
 */

;(function( $ ){


 var Danmu= function (element, options) {
    this.$element	= $(element);  
    this.options	= options;
    $(element).data("nowtime",0);
    $(element).data("danmu_array",options.danmuss);
    $(element).data("opacity",options.opacity);
    $(element).data("paused",1);
    $(element).data("topspace",0);
    $(element).data("bottomspace",0);


    this.$element .css({
		"position":"absolute",
		"left":this.options.left,
		"top":this.options.top,
		"width":this.options.width,
		"height":this.options.height,
		"z-index":this.options.zindex,
		"color":options.default_font_color,
		"overflow":"hidden"
	});
    var heig=this.$element.height();
	var row_conut=parseInt(heig/options.font_size_big);
	var rows_used=new Array();

	$("<div class='timer71452'></div>").appendTo(this.$element );
	this.$timer=$(".timer71452");
	this.$timer.timer({
		delay: 100,
		repeat: options.sumtime,
		autostart: false,
		callback: function( index ) {
			heig=$(element).height();
			//row_conut=parseInt(heig/options.font_size_big);
			if($(element).data("danmu_array")[$(element).data("nowtime")]){
				var danmus=$(element).data("danmu_array")[$(element).data("nowtime")];
				for(var i=0;i<danmus.length;i++){
					var a_danmu="<div class='flying flying2' id='linshi'></div>";
					$(element).append(a_danmu);
					$("#linshi").text(danmus[i].text);
					$("#linshi").css({
						"color":danmus[i].color
						,"text-shadow":" 0px 0px 2px #000000"
						,"-moz-opacity":$(element).data("opacity")
						,"opacity": $(element).data("opacity")
						,"white-space":"nowrap"
						,"font-weight":"bold"
						,"font-family":"SimHei" 
						,"font-size":options.font_size_big
					});
					if (danmus[i].color<"#777777")
						$("#linshi").css({
							"text-shadow":" 0px 0px 2px #FFFFFF"
						});
					if (danmus[i].hasOwnProperty('isnew')){
						$("#linshi").css({"border":"0px solid "+danmus[i].color});
					}
					if( danmus[i].size == 0)  $("#linshi").css("font-size",options.font_size_small);
					if  ( danmus[i].position == 0){
						//var top_local=parseInt(30+(options.height-60)*Math.random());//随机高度
						var row = parseInt(row_conut*Math.random());
						while (rows_used.indexOf(row)>=0 ){
							var row = parseInt(row_conut*Math.random());
						}
						rows_used.push(row);
						//console.log(rows_used.length);
						if (rows_used.length==row_conut){
							rows_used =new Array();
							row_conut=parseInt(heig/options.font_size_big);
						}
						var top_local=(row)*options.font_size_big;

						$("#linshi").css({"position":"absolute"
										,"top":top_local
										,"left":options.width
										 });
						var fly_tmp_name="fly"+parseInt(heig*Math.random()).toString();	
						$("#linshi").attr("id",fly_tmp_name);
						$('#'+fly_tmp_name).animate({left:-$(this).width()*3,},options.speed
							,function(){$(this).remove();}	
						 );
					}
					else if ( danmus[i].position == 1){
						var top_tmp_name="top"+parseInt(10000*Math.random()).toString();
						$("#linshi").attr("id",top_tmp_name)
						$('#'+top_tmp_name).css({
							"width":options.width
							,"text-align":"center"
							,"position":"absolute"
							,"top":(5+$(element).data("topspace"))
								 });
						 $(element).data("topspace",$(element).data("topspace")+options.font_size_big);
						$('#'+top_tmp_name).fadeTo(options.top_botton_danmu_time,$(element).data("opacity"),function(){
							$(this).remove();
							$(element).data("topspace",$(element).data("topspace")-options.font_size_big);
						}
						);						
					}
					else if ( danmus[i].position == 2){
						var bottom_tmp_name="top"+parseInt(10000*Math.random()).toString();
						$("#linshi").attr("id",bottom_tmp_name)
						$('#'+bottom_tmp_name).css({
							"width":options.width
							,"text-align":"center"
							,"position":"absolute"
							,"bottom":0+$(element).data("bottomspace")
								 });
						$(element).data("bottomspace",$(element).data("bottomspace")+options.font_size_big);
						$('#'+bottom_tmp_name).fadeTo(options.top_botton_danmu_time,$(element).data("opacity"),function(){
							$(this).remove();
							$(element).data("bottomspace",$(element).data("bottomspace")-options.font_size_big)
						}
						);
						
					} //else if
				}   // for in danmus
			}  //if (danmus)
				$(element).data("nowtime",$(element).data("nowtime")+1);
			
			
		}
	});		  
};


Danmu.DEFAULTS = {
		left: 0,    
		top: 0 , 
		height: 360,
		width: 640,
		zindex :100,
		speed:20000,
		sumtime:65535	,
		danmuss:{},
		default_font_color:"#FFFFFF",
		font_size_small:16,
		font_size_big:24,
		opacity:"0.9",
		top_botton_danmu_time:6000
	}



Danmu.prototype.danmu_start = function(){	
	this.$timer.timer('start');
	this.$element.data("paused",0);
};

Danmu.prototype.danmu_stop = function(){
	this.$timer.timer('stop');
	$('.flying').remove();
	nowtime=0;
	this.$element.data("paused",1);
	this.$element.data("nowtime",0);
};


Danmu.prototype.danmu_pause = function(){
	this.$timer.timer('pause');
	$('.flying').pause();
	this.$element.data("paused",1);
};


Danmu.prototype.danmu_resume = function(){
	this.$timer.timer('resume');
	$('.flying').resume();
	this.$element.data("paused",0);
};

Danmu.prototype.danmu_hideall= function(){
	$('.flying').remove();

};

Danmu.prototype.add_danmu = function(arg){
	if(this.$element.data("danmu_array")[arg.time]){
		this.$element.data("danmu_array")[arg.time].push(arg);
	}
	else{
		this.$element.data("danmu_array")[arg.time]=new Array();
		this.$element.data("danmu_array")[arg.time].push(arg);
	}

};

	
function Plugin(option,arg) {
    return this.each(function () {
      var $this   = $(this);
      var options = $.extend({}, Danmu.DEFAULTS, typeof option == 'object' && option);
      var data    = $this.data('danmu');
      var action  = typeof option == 'string' ? option : NaN;
      if (!data) $this.data('danmu', (data = new Danmu(this, options)))
      if (action)	data[action](arg);  
    })
};


$.fn.danmu             = Plugin;
$.fn.danmu.Constructor = Danmu;


})(jQuery);


;
(function($) {

	var DanmuPlayer = function(element, options) {
		this.$element = $(element);
		this.options = options;
		url_to_post_danmu = options.url_to_post_danmu;



		$(element).append('<video id="danmu_video" class="video-js vjs-default-skin" width="' + options.width + '" height="' + options.height + '"><source src="' + options.src + '" type="video/mp4" /></video>');
		danmu_video = videojs("#danmu_video", {
			"controls": true,
			"autoplay": false,
			"preload": "auto",
			"loop": false
		}, function() {
			// This is functionally the same as the previous example.

			$(".video-js").append('<div id="danmu71452" >');
			$(".vjs-live-controls").remove();



			function query() {
				$.get(options.url_to_get_danmu, function(data, status) {
					var danmu_from_sql = eval(data);
					for (var i = 0; i < danmu_from_sql.length; i++) {
						console.log(danmu_from_sql[i])
						// var danmu_ls = eval('(' + danmu_from_sql[i] + ')');
						// $('#danmu71452').danmu("add_danmu", danmu_ls);
						send_danmu_diy(danmu_from_sql[i])
					}
				});
			};


			function initer() {
				this.on('firstplay', function(e) {
					// $(".video-js").append('<div id="danmu71452" >');
					if (options.url_to_get_danmu)
						query();
					$(".vjs-control-bar").css({
						"z-index": "500"
					});
					$("#danmu71452").danmu({
						left: 0,
						top: 0,
						width: "100%",
						height: "100%",
						zindex: 100,
						speed: options.speed,
						opacity: options.opacity,
						font_size_small: options.font_size_small,
						font_size_big: options.font_size_big,
						top_botton_danmu_time: options.top_botton_danmu_time
					});
				});

				var socket_status=0;
				var update_time_status=0;
				this.on('play', function(e) {
					console.log('playback has started!');
					$('#danmu71452').data("nowtime", parseInt(danmu_video.currentTime() * 10));
					$('#danmu71452').danmu("danmu_resume");
					
					if(socket_status==0){
						socket_status=1;
						socket(options);
					}
					
					if(update_time_status==0){
						update_time_status=1;
						updateTime(options);
					}


				});


				this.on('pause', function(e) {
					console.log('playback has paused!');
					$('#danmu71452').danmu('danmu_pause');
				});

				this.on('waiting', function(e) {
					console.log('playback has waiting!');

					if (danmu_video.currentTime() == 0) {

						$('#danmu71452').data("nowtime", 0);
					} else {
						$('#danmu71452').data("nowtime", parseInt(danmu_video.currentTime() * 10));
					}
				});

				this.on('ended', function(e) {
					console.log('playback has ended!');
					$('#danmu71452').danmu('danmu_stop');
				});


				this.on('seeked', function(e) {
					$('#danmu71452').danmu('danmu_hideall');
					$('#danmu71452').data("nowtime", parseInt(this.currentTime() * 10));
				});
			}

			videojs.plugin('initer', initer);
			this.initer({
				exampleOption: true
			});

			$("body").append("<div id='tip2' class='tipb' hidden='true'><form  id='danmu_position'>弹幕位置：<input type='radio' checked='checked'  name='danmu_position' value='0' />滚动&nbsp;&nbsp;<input type='radio' name='danmu_position' value='1' />顶端&nbsp;&nbsp;<input type='radio' name='danmu_position' value='2' />底端&nbsp;&nbsp;</form><form  id='danmu_size' >弹幕大小：<input   type='radio' checked='checked'  name='danmu_size' value='1' />大文字&nbsp;&nbsp;<input   type='radio' n name='danmu_size' value='0' />小文子&nbsp;&nbsp;</form>弹幕颜色：<br><div id='danmu_color' /></div></div><div id='tip22' class='tipb' hidden='true'>透明度：<input type='range' name='op' id='op' onchange='op()' value=100 ><br>显示弹幕:<input type='checkbox' checked='checked' id='ishide' value='is' onchange='changehide()'> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;循环播放:<input type='checkbox' id='isloop' value='is' onchange='changeloop()'> </div> ");

			$(".vjs-control-bar").append('<span class="shezhi " id="danmu_send_opt">(&gt;^ω^&lt;)</span>');
			$(".vjs-control-bar").append('<input  role="botton" type="textarea" id="danmu_text" max=300 />'); // -> button 
			$(".vjs-control-bar").append('<button  id="send_danmu" type="button" aria-live="polite" onclick="send_danmu()">发送</botton>');

			$(".vjs-control-bar").append('<span  class="shezhi  vjs-menu-button" id="danmu_shi_opt"  > 視 </span>');




			$(".shezhi").css({
				"cursor": "pointer",
				"line-height": "2.5em",
				"font-family": "Microsoft YaHei,微软雅黑,MicrosoftJhengHei"
			});


			$("#danmu_text").css({
				"width": "40%",
				"left": "auto",
				"right": "auto",
				"opacity": "0.5",
				"font-family": "Microsoft YaHei,微软雅黑,MicrosoftJhengHei"
			});
			$("button").css({
				"opacity": "0.8",
				"font-family": "Microsoft YaHei,微软雅黑,MicrosoftJhengHei"
			});


			$('#danmu_send_opt').scojs_tooltip71452({
				appendTo: '.video-js',
				contentElem: '#tip2',
			});


			$('#danmu_shi_opt').scojs_tooltip71452({
				appendTo: '.video-js',
				contentElem: '#tip22',
	
			});

			$("#danmu_color").colpick({
				colorScheme: 'dark',
				flat: true,
				layout: 'hex',
				submit: 0,
				color: "#ffffff",
				onChange: function(hsb, hex, rgb, el, bySetColor) {
					danmu_color = "#" + hex
				}
			}).css('background-color', '#07141e');;



		});

	
			


	};



	DanmuPlayer.DEFAULTS = {
		height: 450,
		width: 800,
		src: "shsn.mp4",
		speed: 20000,
		danmuss: {},
		default_font_color: "#FFFFFF",
		font_size_small: 16,
		font_size_big: 28,
		opacity: "1",
		top_botton_danmu_time: 6000,
		url_to_get_danmu: "",
		url_to_post_danmu: "",
		url_to_update_time: "",
	}



	function Plugin(option, arg) {
		return this.each(function() {
			var $this = $(this);
			var options = $.extend({}, DanmuPlayer.DEFAULTS, typeof option == 'object' && option);
			var data = $this.data('danmuplayer');
			var action = typeof option == 'string' ? option : NaN;
			if (!data) $this.data('danmuplayer', (data = new DanmuPlayer(this, options)))
			if (action) data[action](arg);
		})
	};


	$.fn.danmuplayer = Plugin;
	$.fn.danmuplayer.Constructor = DanmuPlayer;



})(jQuery);


var is_loop = false;
var url_to_post_danmu = "";
var danmu_color = "#ffffff";
jQuery(document).ready(function() {
	jQuery("body").keydown(function(event) {
		if (event.which == 13) {
			console.log("enter")
			send_danmu();
			return false
		}
	});
	
	
});

function send_danmu() {
	var text = document.getElementById('danmu_text').value;
	var color = danmu_color;
	var position_select = jQuery("[name='danmu_position']").filter(":checked");
	var position = position_select.attr("value")
	var position_size = jQuery("[name='danmu_size']").filter(":checked");
	var size = position_size.attr("value");
	var time = jQuery('#danmu71452').data("nowtime") + 5;
	var text_obj = '{ "text":"' + text + '","color":"' + color + '","size":"' + size + '","position":"' + position + '","time":' + time + '}';
	console.log(url_to_post_danmu);
	if (url_to_post_danmu)
		jQuery.post(url_to_post_danmu, {
			danmu: text_obj
		});
	var text_obj = '{ "text":"' + text + '","color":"' + color + '","size":"' + size + '","position":"' + position + '","time":' + time + ',"isnew":""}';
	var new_obj = eval('(' + text_obj + ')');
	jQuery('#danmu71452').danmu("add_danmu", new_obj);
	console.log(text_obj);
	document.getElementById('danmu_text').value = '';
};


function send_danmu2(str) {
	var text = str;
	var color = danmu_color;
	var position_select = jQuery("[name='danmu_position']").filter(":checked");
	var position = position_select.attr("value")
	var position_size = jQuery("[name='danmu_size']").filter(":checked");
	var size = position_size.attr("value");
	var time = jQuery('#danmu71452').data("nowtime") + 5;
	var text_obj = '{ "text":"' + text + '","color":"' + color + '","size":"' + size + '","position":"' + position + '","time":' + time + '}';
	console.log(url_to_post_danmu);
	if (url_to_post_danmu)
		jQuery.post(url_to_post_danmu, {
			danmu: text_obj
		});
	var text_obj = '{ "text":"' + text + '","color":"' + color + '","size":"' + size + '","position":"' + position + '","time":' + time + ',"isnew":""}';
	var new_obj = eval('(' + text_obj + ')');
	jQuery('#danmu71452').danmu("add_danmu", new_obj);
	console.log(text_obj);
	document.getElementById('danmu_text').value = '';
};


function send_danmu_diy(str) {
	var text = str.message;
	var color = str.color;
	var position = str.position;
	var size = str.size;
	var time = str.time;
	var isnew = str.isnew;


	var text_obj = '{ "text":"' + text + '","color":"' + color + '","size":"' + size + '","position":"' + position + '","time":' + time + '}';

	var text_obj = '{ "text":"' + text + '","color":"' + color + '","size":"' + size + '","position":"' + position + '","time":' + time + ',"isnew":"'+isnew+'"}';
	var new_obj = eval('(' + text_obj + ')');
	jQuery('#danmu71452').danmu("add_danmu", new_obj);
};

function op() {
	var op = document.getElementById('op').value;
	op = op / 100;
	jQuery('#danmu71452').data("opacity", op);
	jQuery(".flying").css({
		"opacity": op
	});
}

function changeloop() {
	if (document.getElementById("isloop").checked)
		danmu_video.loop(true)
	else
		danmu_video.loop(false)
}

function changehide() {
	var op = document.getElementById('op').value;
	op = op / 100;
	if (document.getElementById("ishide").checked) {
		jQuery('#danmu71452').data("opacity", op);
		jQuery(".flying").css({
			"opacity": op
		});
	} else {
		jQuery('#danmu71452').data("opacity", 0);
		jQuery(".flying").css({
			"opacity": 0
		});
	}
}


function socket(options){
	var socket;
	console.log(options)
	if(typeof(WebSocket) == "undefined") {
		console.log("您的浏览器不支持WebSocket");
	}else{
		console.log("您的浏览器支持WebSocket");
		//实现化WebSocket对象，指定要连接的服务器地址与端口  建立连接
		var socketUrl=options.socket_url;
		socketUrl=socketUrl.replace("https","ws").replace("http","ws");
		console.log(socketUrl);
		if(socket!=null){
			socket.close();
			socket=null;
		}
		socket = new WebSocket(socketUrl);


		//打开事件
		socket.onopen = function() {
			console.log("websocket已打开");

			//socket.send("这是来自客户端的消息" + location.href + new Date());
		};
		//获得消息事件
		socket.onmessage = function(msg) {
			//发现消息进入    开始处理前端触发逻辑

			var jsonList=JSON.parse( msg.data );
			jsonList.forEach(function (j) {
				console.log(j)
				send_danmu_diy(j)
			});

		};
		//关闭事件
		socket.onclose = function() {
			console.log("websocket已关闭");
		};
		//发生了错误事件
		socket.onerror = function() {
			console.log("websocket发生了错误");
		}
	}
}


function updateTime(options){
		update_time_status = 1;
		window.setInterval(function() {
			var time = jQuery('#danmu71452').data("nowtime")+20;
			// console.log('time:'+time)
	
			$.get(options.url_to_update_time+'?time='+time, function(data, status) {
			// console.log(data);
		});
			},1000)
}
