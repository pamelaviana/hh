class BarChart {
  constructor() {
    this.type = 'bar';
    this.data = {
      labels: ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug'],
      datasets: [
        {
          label: 'Systolic',
          backgroundColor: '#4e73df',
          borderColor: '#4e73df',
          data: [0, 0, 0, 0, 0, 0, 0, 0]
        },
        {
          label: 'Diastolic',
          backgroundColor: '#36b9cc',
          borderColor: '#36b9cc',
          data: [0, 0, 0, 0, 0, 0, 0, 0]
        }
      ]
    };
    this.options = {
      maintainAspectRatio: false,
      legend: {
        display: true,
        labels: {
          fontStyle: 'normal',
          fontColor: '#666'
        }
      },
      title: {
        fontStyle: 'normal',
        text:"Daily Measurment",
        display:true,
        position:"bottom"
      },
      scales: {
        xAxes: [
          {
            gridLines: {
              drawBorder: false,
              drawTicks: false,
              borderDash: [2],
              zeroLineBorderDash: [2],
              drawOnChartArea: false
            },
            ticks: {
              fontSize: '11',
              fontStyle: 'normal',
              beginAtZero: false,
              padding: 20
            }
          }
        ],
        yAxes: [
          {
            gridLines: {
              drawBorder: false,
              drawTicks: false,
              borderDash: [2],
              zeroLineBorderDash: [2]
            },
            ticks: {
              fontSize: '11',
              fontStyle: 'normal',
              beginAtZero: false,
              padding: 20
            }
          }
        ]
      }
    };
  }
}