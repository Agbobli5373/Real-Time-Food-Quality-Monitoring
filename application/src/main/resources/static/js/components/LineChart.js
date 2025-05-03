const { useEffect } = React;

export default function LineChart({ data, field, label }) {
  const id = `${field}Chart`;
  useEffect(() => {
    // Destroy existing chart if present
    if (window.chartRegistry && window.chartRegistry[id]) {
      window.chartRegistry[id].destroy();
    }
    const canvas = document.getElementById(id);
    if (!canvas) return;
    const ctx = canvas.getContext("2d");

    const thresholdConfig =
      field === "temperature"
        ? { value: 8.0, color: "rgba(229, 62, 62, 0.2)", label: "Max Temp" }
        : {
            value: 50.0,
            color: "rgba(49, 130, 206, 0.2)",
            label: "Min Humidity",
          };

    const chart = new Chart(ctx, {
      type: "line",
      data: {
        labels: data
          .map((ev) => new Date(ev.timestamp).toLocaleTimeString())
          .reverse(),
        datasets: [
          {
            label,
            data: data.map((ev) => ev[field]).reverse(),
            borderColor: field === "temperature" ? "#ef4444" : "#3b82f6",
            backgroundColor:
              field === "temperature"
                ? "rgba(239, 68, 68, 0.1)"
                : "rgba(59, 130, 246, 0.1)",
            borderWidth: 2,
            fill: true,
            tension: 0.3,
            pointRadius: 3,
            pointHoverRadius: 5,
          },
          {
            label: thresholdConfig.label,
            data: new Array(data.length).fill(thresholdConfig.value),
            borderColor: thresholdConfig.color,
            borderWidth: 2,
            borderDash: [5, 5],
            fill: false,
            pointRadius: 0,
          },
        ],
      },
      options: {
        animation: false,
        scales: {
          y: { beginAtZero: true, grid: { color: "#e5e7eb" } },
          x: { grid: { display: false } },
        },
        plugins: { legend: { position: "top" } },
        responsive: true,
        maintainAspectRatio: false,
      },
    });

    // Register chart
    window.chartRegistry = window.chartRegistry || {};
    window.chartRegistry[id] = chart;

    return () => chart.destroy();
  }, [data]);

  return React.createElement(
    "div",
    { className: "chart-container" },
    React.createElement("canvas", { id })
  );
}
