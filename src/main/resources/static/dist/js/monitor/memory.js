document.body.style.display = "block";
var arr = [];
$.ajax({
    type: "GET",
    url: '/monitor/getMemoryInfo?type=0',
    dataType: 'json',
    async: false,
    success: function (res) {
        if (res.code == 1) {
            arr = res.data;
        }
    },
    error: function (res) {
        layer.msg("失败");
    }
});
var options3 = {
    series: [],
    chart: {
        type: "donut",
        fontFamily: 'inherit',
        height: 240,
        sparkline: {
            enabled: true
        },
        animations: {
            enabled: true
        },
    },
    noData: {
        text: '数据不存在！'
    },
    labels: ["使用", "剩余"],
    theme: {
        monochrome: {
            enabled: true
        }
    },
    plotOptions: {
        pie: {
            dataLabels: {
                offset: -5
            }
        }
    },
    title: {
        text: "内存使用占比"
    },
    dataLabels: {
        formatter(val, opts) {
            const name = opts.w.globals.labels[opts.seriesIndex]
            return [name, val.toFixed(1) + '%']
        }
    },
    legend: {
        show: true
    }
};
options3.series = arr;

var chart3 = new ApexCharts(document.querySelector("#chart-total-sales"), options3);
chart3.render();


// 第一个
var options = {
    chart: {
        type: "line",
        fontFamily: 'inherit',
        height: 192,
        parentHeightOffset: 0,
        toolbar: {
            show: true,
        },
        animations: {
            enabled: true
        },
    },
    fill: {
        opacity: 1,
    },
    stroke: {
        width: 2,
        lineCap: "round",
        curve: "smooth",
    },
    series: [],
    title: {
        text: '内存占用波动'
    },
    noData: {
        text: '数据不存在！'
    },
    grid: {
        padding: {
            top: -20,
            right: 0,
            left: -4,
            bottom: -4
        },
        strokeDashArray: 4,
        xaxis: {
            lines: {
                show: true
            }
        },
    },
    xaxis: {
        labels: {
            padding: 0
        },
        tooltip: {
            enabled: true
        },
        type: 'datetime',
    },
    yaxis: {
        labels: {
            padding: 4
        },
    },
    colors: ["#3b5998", "#1da1f2", "#ea4c89"],
    legend: {
        show: true,
        position: 'bottom',
        height: 32,
        offsetY: 8,
        markers: {
            width: 8,
            height: 8,
            radius: 100,
        },
        itemMargin: {
            horizontal: 8,
        },
    }
};

// 第二个
var operation1 = {
    chart: {
        type: "bar",
        fontFamily: 'inherit',
        height: 320,
        parentHeightOffset: 0,
        toolbar: {
            show: true,
        },
        animations: {
            enabled: true
        },
    },
    plotOptions: {
        bar: {
            columnWidth: '50%',
        }
    },
    dataLabels: {
        enabled: false,
    },
    fill: {
        opacity: 1,
    },
    series: [],
    title: {
        text: '内存占用波动'
    },
    noData: {
        text: '数据不存在！'
    },
    grid: {
        padding: {
            top: -20,
            right: 0,
            left: -4,
            bottom: -4
        },
        strokeDashArray: 4,
    },
    xaxis: {
        labels: {
            padding: 0
        },
        tooltip: {
            enabled: true
        },
        axisBorder: {
            show: true,
        },
    },
    yaxis: {
        labels: {
            padding: 4
        },
    },
    colors: ["#206bc4"],
    legend: {
        show: true,
    }
};

var chart = new ApexCharts(document.querySelector("#chart-social-referrals"), options);
chart.render();
var chart1 = new ApexCharts(document.querySelector("#chart-tasks-overview"), operation1);
chart1.render();


$.getJSON('/monitor/getChartsData?type=0', function (response) {
    if (1 == response.code) {
        chart.updateSeries([{
            name: '内存使用',
            data: response.data
        }]);
        chart1.updateSeries([{
            name: '内存使用',
            data: response.data
        }]);
    }
});
