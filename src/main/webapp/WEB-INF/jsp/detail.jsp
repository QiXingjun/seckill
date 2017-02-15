<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="common/tag.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <title>秒杀详情页</title>
    <%--静态引入--%>
    <%@include file="common/head.jsp" %>
</head>
<body>
<div class="container">
    <div class="panel panel-default text-center">
        <div class="panel-heading">
            <h2>${detail.name}</h2>
        </div>
    </div>
    <div class="panel-body">
        <h2 class="text-danger text-center">
            <%--显示time图标--%>
            <span class="glyphicon glyphicon-time"></span>
            <%--显示倒计时--%>
            <span class="glyphicon" id="seckill-box"></span>
        </h2>
    </div>
</div>
<%--登录弹出层，输入电话--%>
<div id="killPhoneModal" class="modal fade">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h3 class="modal-title text-center">
                    <span class="glyphicon glyphicon-phone"></span>秒杀电话：
                </h3>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="col-xs-8 col-xs-offset-2">
                        <input type="text" name="killphone" id="killphoneKey" placeholder="请填写手机号"
                               class="form-control"/>
                    </div>
                </div>
            </div>
            <%--验证信息--%>
            <div class="modal-footer">
                <span id="killphoneMessage" class="glyphicon"></span>
                <button type="button" id="killPhoneBtn" class="btn btn-success">
                    <span class="glyphicon glyphicon-phone"></span>
                    Submit
                </button>
            </div>
        </div>
    </div>
</div>

</body>
<!-- jQuery (Bootstrap 的 JavaScript 插件需要引入 jQuery) -->
<!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
<script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

<%--使用CDN获取公共js  ：http://www.bootcdn.cn/--%>
<%--JQuery Cookie操作插件--%>
<script src="https://cdn.bootcss.com/jquery-cookie/1.4.1/jquery.cookie.js"></script>
<%--JQuery countDown倒计时插件--%>
<script src="https://cdn.bootcss.com/jquery.countdown/2.1.0/jquery.countdown.js"></script>

<%--开始编写交互逻辑--%>
<script src="/resources/script/seckill.js" type="text/javascript"></script>
<script type="text/javascript">
    $(function(){
        //使用EL表达式传入参数
        seckill.detail.init({
            seckillId : ${detail.seckillId},
            startTime : ${detail.startTime.time},//毫秒
            endTime : ${detail.endTime.time}
        });
    });
</script>
</html>
