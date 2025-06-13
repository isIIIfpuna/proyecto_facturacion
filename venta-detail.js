document.addEventListener("DOMContentLoaded", function () {
    const urlParams = new URLSearchParams(window.location.search);
    const ventaId = urlParams.get("id");

    const opcionesCredito = document.getElementById("opcionesCredito");

    if (ventaId) {
        fetch(`http://localhost:8080/backend-inge3/sale/${ventaId}`)
            .then(response => response.json())
            .then(data => {
                document.getElementById("cliente-nombre").textContent = data.customer.name || "-";
                document.getElementById("cliente-email").textContent = data.customer.email || "-";
                document.getElementById("cliente-telefono").textContent = data.customer.phone || "-";
                document.getElementById("cliente-ci").textContent = data.customer.ci_ruc || "-";

                const tipoPago = data.payment_type;
                document.getElementById("contado").checked = tipoPago === "CO";
                document.getElementById("credito").checked = tipoPago === "CR";

                if (tipoPago === "CR") {
                    opcionesCredito.classList.remove("d-none");

                    fetch(`http://localhost:8080/backend-inge3/installment/invoice/${data.sale_id}`)
                        .then(response => response.json())
                        .then(cuotas => {
                            document.getElementById("cuotas").value = cuotas.length;

                            const listaFechas = document.getElementById("lista-fechas");
                            listaFechas.innerHTML = "";

                            cuotas.forEach(cuota => {
                                const li = document.createElement("li");
                                const fecha = new Date(cuota.due_date);
                                const fechaFormateada = fecha.toLocaleDateString('es-PY', {
                                    day: '2-digit', month: '2-digit', year: 'numeric'
                                });

                                li.textContent = `Cuota ${cuota.installment_number}: ${fechaFormateada}`;
                                li.classList.add("list-group-item", "bg-success", "text-white");
                                listaFechas.appendChild(li);
                            });
                        })
                        .catch(err => {
                            console.error("Error al obtener cuotas:", err);
                        });
                }

                const tbody = document.getElementById("detalle-productos");
                tbody.innerHTML = "";

                data.sale_items.forEach(prod => {
                    const fila = document.createElement("tr");
                    fila.innerHTML = `
                        <td>${prod.product_name || prod.product_id}</td>
                        <td>${parseInt(prod.unit_price).toLocaleString('es-PY')}</td>
                        <td>${prod.quantity}</td>
                        <td>${parseInt(prod.sub_total).toLocaleString('es-PY')}</td>
                    `;
                    tbody.appendChild(fila);
                });

                const totalVenta = document.getElementById("total-venta");
                totalVenta.textContent = "Gs. " + parseInt(data.total_amount).toLocaleString('es-PY');
            })
            .catch(err => {
                console.error("Error al cargar la venta:", err);
                alert("Error al cargar datos de la venta.");
            });
    } else {
        alert("No se especificó un ID de venta en la URL.");
    }
});
