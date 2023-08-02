let barChartId = document.getElementById("barChartId");

var loadBarChart = function (self) {
    let data = self.getAttribute('data-chart');

    let barChart = new BarChart();
    if (data == null || data == undefined || data.length == 0) {
        console.log('No bar chart data');
    } else {
        console.log('bar chart data found');
        let chartData = JSON.parse(data);
        barChart.data.labels = chartData.labels;
        barChart.data.datasets = chartData.dataset;
    }
    barChartId.setAttribute('data-bss-chart', JSON.stringify(barChart));
}
loadBarChart(barChartId);