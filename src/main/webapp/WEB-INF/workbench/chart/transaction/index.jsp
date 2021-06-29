<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <script src="/crm/jquery/echarts.min.js"></script>
    <script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
</head>

<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 1000px;height:400px;"></div>
<script>
    $.post("/crm/bar/transactionEcharts",{},function (data) {
        //console.log(data);
        var titles = data.t[0];
        var data = data.t[1];

    // 基于准备好的dom，初始化echarts实例
    var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
    var option = {
        title: {
            text: '交易统计图表'
        },
        tooltip: {},
        xAxis: {
            data: titles
        },
        yAxis: {},
        series: [{
            name: '销量',
            type: 'bar',
            data: data
        }]
    };

    // 使用刚指定的配置项和数据显示图表。
    myChart.setOption(option);
    },'json')
</script>

</body>
</html>