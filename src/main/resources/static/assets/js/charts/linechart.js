class LineChart {
  constructor() {
    this.type = 'line';
    this.data = {
      labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug"],
      datasets: [
        {
          label: "Blood Pressure",
          fill: true,
          data: [69, 65, 68, 68, 73, 68, 68, 70],
          backgroundColor: "rgba(78, 115, 223, 0.05)",
          borderColor: "rgba(78, 115, 223, 1)"
        }
      ]
    };
    this.options = {
      maintainAspectRatio: false,
      legend: {
        display: true,
        labels: {
          fontStyle: "normal"
        }
      },
      title: {
        fontStyle: "normal"
      },
      scales: {
        xAxes: [
          {
            gridLines: {
              color: "rgb(234, 236, 244)",
              zeroLineColor: "rgb(234, 236, 244)",
              drawBorder: false,
              drawTicks: false,
              borderDash: [2],
              zeroLineBorderDash: [2],
              drawOnChartArea: false
            },
            ticks: {
              fontColor: "#858796",
              fontStyle: "normal",
              padding: 20
            }
          }
        ],
        yAxes: [
          {
            gridLines: {
              color: "rgb(234, 236, 244)",
              zeroLineColor: "rgb(234, 236, 244)",
              drawBorder: false,
              drawTicks: false,
              borderDash: [2],
              zeroLineBorderDash: [2]
            },
            ticks: {
              fontColor: "#858796",
              fontStyle: "normal",
              padding: 20
            }
          }
        ]
      }
    };
  }
}