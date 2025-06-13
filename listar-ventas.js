async function cargarVentas() {
    try {
        const response = await fetch('http://localhost:8080/backend-inge3/sale', {
            method: 'GET',
            headers: {
                'Accept': 'application/json'
            }
        });
        if (!response.ok) throw new Error('Error al obtener ventas');

        const ventas = await response.json();
        const tbody = document.getElementById("tabla-ventas");
        tbody.innerHTML = "";

        ventas.forEach(venta => {
            const fila = document.createElement("tr");
            fila.innerHTML = `
                <td>${venta.sale_id}</td>
                <td>${venta.customer.name}</td>
                <td>${venta.total_amount.toLocaleString('es-PY')} Gs.</td>
                <td>${formatearFecha(venta.sale_date)}</td>
                <td>
                    <i class="fa-solid fa-trash" title="Eliminar" onclick="eliminarPedido(${venta.sale_id})" style="cursor:pointer; color: #dc3545;"></i>
                    <i class="fa-solid fa-pen-to-square" title="Editar" onclick="editarPedido(${venta.sale_id})" style="cursor:pointer; color: #0d6efd;"></i>
                    <i class="fa-solid fa-eye" title="Ver" onclick="verPedido(${venta.sale_id})" style="cursor:pointer; color: #198754;"></i>
                </td>
            `;
            tbody.appendChild(fila);
        });
    } catch (err) {
        console.error("Error al cargar ventas:", err);
        alert("Error al cargar las ventas");
    }
}

function formatearFecha(fechaISO) {
    const fecha = new Date(fechaISO);
    return fecha.toLocaleDateString("es-ES");
}

async function eliminarPedido(id) {
    if (!confirm(`¿Seguro que deseas eliminar la venta ${id}?`)) return;
    try {
        const res = await fetch(`http://localhost:8080/backend-inge3/sale/${id}`, {
            method: "DELETE"
        });
        if (!res.ok) throw new Error('No se pudo eliminar la venta');
        alert("Venta eliminada correctamente");
        cargarVentas();
    } catch (err) {
        console.error("Error al eliminar:", err);
        alert("Error al eliminar venta");
    }
}

function editarPedido(id) {
    window.location.href = `form-ventas.html?id=${id}`;
}

function verPedido(id) {
    window.location.href = `venta-detail.html?id=${id}`;
}

window.onload = cargarVentas;
