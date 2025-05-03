import Summary from "./Summary.js";
import LineChart from "./LineChart.js";
import AnomalyList from "./AnomalyList.js";

const { useState, useEffect } = React;

export default function Dashboard() {
  // Application state hooks
  const [events, setEvents] = useState([]);
  const [stats, setStats] = useState({});
  const [connected, setConnected] = useState(false);
  const [error, setError] = useState(null);

  // Reset stats when events update
  useEffect(() => {
    if (events.length > 0) {
      const temps = events.map((ev) => ev.temperature);
      const hums = events.map((ev) => ev.humidity);
      setStats({
        tmin: Math.min(...temps).toFixed(1),
        tmax: Math.max(...temps).toFixed(1),
        tavg: (temps.reduce((a, b) => a + b, 0) / temps.length).toFixed(1),
        hmin: Math.min(...hums).toFixed(1),
        hmax: Math.max(...hums).toFixed(1),
        havg: (hums.reduce((a, b) => a + b, 0) / hums.length).toFixed(1),
      });
    }
  }, [events]);

  // Establish SSE connection
  useEffect(() => {
    const source = new EventSource("/events/stream");
    source.addEventListener("sensorEvent", (e) => {
      const event = JSON.parse(e.data);
      setEvents((prev) => [event, ...prev].slice(0, 20));
    });
    source.addEventListener("open", () => {
      setConnected(true);
    });
    source.addEventListener("error", () => {
      setConnected(false);
      setError("Connection lost");
    });
    return () => {
      source.close();
    };
  }, []);

  return React.createElement(
    "div",
    { className: "dashboard-container" },
    React.createElement(
      "h1",
      { className: "text-4xl font-semibold mb-4 text-center" },
      "Food Quality Dashboard"
    ),
    React.createElement(Summary, { stats }),
    React.createElement(
      "div",
      { className: "charts-grid" },
      React.createElement(LineChart, {
        data: events,
        field: "temperature",
        label: "Temperature (°C)",
      }),
      React.createElement(LineChart, {
        data: events,
        field: "humidity",
        label: "Humidity (%)",
      })
    ),
    React.createElement(AnomalyList, { events }),
    React.createElement(
      "div",
      {
        className: `connection-status ${
          connected ? "connected" : "disconnected"
        }`,
      },
      React.createElement("span", {
        className: `w-2 h-2 rounded-full ${
          connected ? "bg-success" : "bg-danger"
        }`,
      }),
      React.createElement("span", null, connected ? "Live" : "Offline")
    )
  );
}
