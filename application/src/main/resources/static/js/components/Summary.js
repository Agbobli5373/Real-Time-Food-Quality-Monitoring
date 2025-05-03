const { createElement } = React;

export default function Summary({ stats }) {
  if (!stats.tmin) {
    return createElement(
      "div",
      { className: "stats" },
      createElement("h2", null, "Summary (last 20)"),
      createElement("p", null, "Waiting for data...")
    );
  }

  return createElement(
    "div",
    { className: "stats" },
    createElement("h2", null, "Summary (last 20)"),
    createElement(
      "div",
      {
        style: { display: "grid", gridTemplateColumns: "1fr 1fr", gap: "1rem" },
      },
      createElement(
        "div",
        null,
        createElement(
          "h3",
          { style: { margin: "0 0 0.5rem", color: "#e41a1c" } },
          "Temperature"
        ),
        createElement(
          "ul",
          { style: { listStyle: "none", padding: 0 } },
          createElement("li", null, `Min: ${stats.tmin}°C`),
          createElement("li", null, `Max: ${stats.tmax}°C`),
          createElement("li", null, `Avg: ${stats.tavg}°C`)
        )
      ),
      createElement(
        "div",
        null,
        createElement(
          "h3",
          { style: { margin: "0 0 0.5rem", color: "#377eb8" } },
          "Humidity"
        ),
        createElement(
          "ul",
          { style: { listStyle: "none", padding: 0 } },
          createElement("li", null, `Min: ${stats.hmin}%`),
          createElement("li", null, `Max: ${stats.hmax}%`),
          createElement("li", null, `Avg: ${stats.havg}%`)
        )
      )
    )
  );
}
