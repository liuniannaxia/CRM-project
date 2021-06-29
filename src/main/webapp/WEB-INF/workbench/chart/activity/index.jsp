<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<meta charset="UTF-8">
    <script src="/crm/jquery/echarts.min.js"></script>
    <script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
</head>

<body>
<!-- 为 ECharts 准备一个具备大小（宽高）的 DOM -->
<div id="main" style="width: 600px;height:400px;"></div>
<script>
    $.post("/crm/bar/activityEcharts",{},function (data) {


    // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main'));

    // 指定图表的配置项和数据
      option = {
        title: {
            text: '某站点用户访问来源',
            subtext: '纯属虚构',
            left: 'center'
        },
        tooltip: {
            trigger: 'item'
        },
        legend: {
            orient: 'vertical',
            left: 'left',
        },
        series: [
            {
                type: 'pie',
                radius: '50%',
                data: data,
                emphasis: {
                    itemStyle: {
                        shadowBlur: 10,
                        shadowOffsetX: 0,
                        shadowColor: 'rgba(0, 0, 0, 0.5)'
                    }
                }
            }
        ]
    };
        myChart.setOption(option);
    },'json')
</script>

</body>
</html>