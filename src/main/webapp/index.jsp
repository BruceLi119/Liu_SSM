<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Title</title>
</head>
<script type="text/javascript"
        src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script type="text/javascript">
    $(function(){
        $("#testJson").click(function(){
            $.ajax({
                type:"post",
                url:"${pageContext.request.contextPath}/sendMessage/send",
                contentType:"application/json;charset=utf-8",
                data:'{"style":"紧急停水","title":"你猜","sTime":"5月19",' +
                '"eTime":"5月20","stopWaterArea":"高新区","stopWaterStyle":"无"}',
                dataType:"json",
                success:function(data){
                    alert(data);
                }
            });
        });
    })
</script>
<body>

    <!-- 测试异步请求 -->
    <input type="button" value="测试 ajax 请求 json 和响应 json" id="testJson"/><br>

    <a href="account/findAll">测试</a>

    <h3>测试保存</h3>
    <form action="account/save" method="post">
        姓名：<input type="text" name="name"><br>
        金额：<input type="text" name="money"><br>
        <input type="submit" value="保存">
    </form>
</body>
</html>
