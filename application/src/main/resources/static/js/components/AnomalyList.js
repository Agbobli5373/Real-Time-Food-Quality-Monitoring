const { createElement, useState } = React;

export default function AnomalyList({ events }) {
  const [filter, setFilter] = useState("");
  const [page, setPage] = useState(1);
  const eventsPerPage = 10;

  // Filter and paginate events
  const filtered = events.filter(
    (ev) =>
      filter === "" ||
      ev.deviceId.toLowerCase().includes(filter.toLowerCase()) ||
      ev.temperature.toString().includes(filter) ||
      ev.humidity.toString().includes(filter)
  );
  const totalPages = Math.ceil(filtered.length / eventsPerPage);
  const pageItems = filtered.slice(
    (page - 1) * eventsPerPage,
    page * eventsPerPage
  );

  // Check anomaly
  const isAnomaly = (ev) => ev.temperature > 8.0 || ev.humidity < 50.0;

  // Style for list item
  const getStyle = (ev) => {
    const anomaly = isAnomaly(ev);
    return {
      display: "flex",
      alignItems: "center",
      gap: "8px",
      padding: "12px",
      marginBottom: "8px",
      backgroundColor: anomaly ? "rgba(229, 62, 62, 0.05)" : "white",
      color: anomaly ? "#e53e3e" : "#2d3748",
      borderRadius: "8px",
      boxShadow: "0 1px 2px rgba(0,0,0,0.05)",
    };
  };

  // Icon for status
  const getIcon = (ev) => {
    const anomaly = isAnomaly(ev);
    return createElement(
      "span",
      {
        style: {
          width: "24px",
          height: "24px",
          display: "flex",
          alignItems: "center",
          justifyContent: "center",
          borderRadius: "50%",
          color: "white",
          backgroundColor: anomaly ? "#e53e3e" : "#48bb78",
        },
      },
      anomaly ? "⚠️" : "✓"
    );
  };

  // Prepare list items
  const listItems = pageItems.map((ev, i) =>
    createElement(
      "li",
      { key: i, style: getStyle(ev) },
      getIcon(ev),
      createElement(
        "div",
        { style: { flex: 1 } },
        createElement(
          "div",
          {
            style: {
              display: "flex",
              justifyContent: "space-between",
              marginBottom: "4px",
            },
          },
          createElement("strong", null, `Device ${ev.deviceId}`),
          createElement(
            "span",
            { style: { fontSize: "0.8rem", color: "#718096" } },
            new Date(ev.timestamp).toLocaleTimeString()
          )
        ),
        createElement(
          "div",
          { style: { display: "flex", gap: "1rem" } },
          createElement(
            "span",
            { style: { color: ev.temperature > 8.0 ? "#e53e3e" : "#2d3748" } },
            `${ev.temperature.toFixed(1)}°C`
          ),
          createElement(
            "span",
            { style: { color: ev.humidity < 50.0 ? "#e53e3e" : "#2d3748" } },
            `${ev.humidity.toFixed(1)}%`
          )
        )
      )
    )
  );

  return createElement(
    "div",
    { className: "stats events-container" },
    createElement("h2", null, "Recent Events"),
    createElement("input", {
      type: "text",
      value: filter,
      placeholder: "Search by device, temp, or humidity...",
      onChange: (e) => {
        setFilter(e.target.value);
        setPage(1);
      },
      style: {
        width: "100%",
        padding: "8px",
        marginBottom: "1rem",
        borderRadius: "4px",
        border: "1px solid #ddd",
      },
    }),
    createElement(
      "ul",
      { className: "events-list", style: { listStyle: "none", padding: 0 } },
      listItems
    ),
    createElement(
      "div",
      { className: "pagination" },
      createElement(
        "button",
        {
          onClick: () => setPage((p) => Math.max(1, p - 1)),
          disabled: page === 1,
        },
        "← Previous"
      ),
      createElement(
        "span",
        { className: "pagination-info" },
        `${(page - 1) * eventsPerPage + 1}-${Math.min(
          page * eventsPerPage,
          filtered.length
        )} of ${filtered.length}`
      ),
      createElement(
        "button",
        {
          onClick: () => setPage((p) => Math.min(totalPages, p + 1)),
          disabled: page === totalPages,
        },
        "Next →"
      )
    )
  );
}
