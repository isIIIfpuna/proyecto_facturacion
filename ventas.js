let listaProductos = [];
let listaClientes = [];

async function cargarProductos() {
    const res = await fetch("http://localhost:8080/backend-inge3/product");
    listaProductos = await res.json();
    actualizarSelects();
}

function actualizarSelects() {
    const selects = document.querySelectorAll(".producto-selector");

    selects.forEach(select => {
        const $select = $(select);
        const valorSeleccionado = $select.val();

        $select.off("change").empty().append('<option value="">Seleccione Producto</option>');

        listaProductos.forEach(p => {
            const option = new Option(p.name, p.product_id, false, false);
            option.setAttribute("data-precio", p.price);
            $select.append(option);
        });

        $select.select2({width: '100%'});

        if (valorSeleccionado) {
            $select.val(valorSeleccionado).trigger('change');
        }

        $select.on("change", function () {
            const selectedOption = this.selectedOptions[0];
            const precio = selectedOption?.getAttribute("data-precio") || 0;
            const fila = this.closest("tr");
            fila.querySelector(".precio").value = precio;
            calcularTotalFila(fila);
        });
    });
}

function calcularTotalFila(fila) {
    const cantidad = parseFloat(fila.querySelector(".cantidad").value) || 0;
    const precio = parseFloat(fila.querySelector(".precio").value) || 0;
    const subtotal = cantidad * precio;
    const totalInput = fila.querySelector(".total");

    totalInput.dataset.raw = subtotal.toFixed(2);
    totalInput.value = subtotal.toLocaleString("es-ES");

    calcularTotalGeneral();
}

function calcularTotalGeneral() {
    let total = 0;
    document.querySelectorAll("#tablaProductos tbody tr").forEach(fila => {
        const subtotal = parseFloat(fila.querySelector(".total").dataset.raw) || 0;
        total += subtotal;
    });
    document.getElementById("totalGeneral").textContent = total.toLocaleString("es-ES");
}

function toggleOpcionesCredito() {
    const esCredito = document.getElementById("credito").checked;
    document.getElementById("opcionesCredito").classList.toggle("d-none", !esCredito);

    if (!esCredito) {
        document.getElementById("esIrregular").checked = false;
        document.getElementById("diasInputs").innerHTML = "";
    }
}

function generarCamposDias(cantidad) {
    const contenedor = document.getElementById("diasInputs");
    contenedor.innerHTML = "";

    for (let i = 1; i <= cantidad; i++) {
        const div = document.createElement("div");
        div.className = "mb-1 d-flex align-items-center";

        const label = document.createElement("label");
        label.className = "form-label me-2";
        label.style.width = "160px";
        label.textContent = `Día de Cobro Cuota ${i}:`;

        const input = document.createElement("input");
        input.type = "number";
        input.className = "form-control dias-cobro";
        input.placeholder = "Ej: 30";
        input.min = "1";
        input.required = true;
        input.style.flex = "1";

        div.appendChild(label);
        div.appendChild(input);
        contenedor.appendChild(div);
    }
}

async function cargarClientes() {
    const res = await fetch("http://localhost:8080/backend-inge3/customer");
    listaClientes = await res.json();

    const $select = $("#clienteExistente");
    $select.empty().append('<option value="">-- Nuevo Cliente --</option>');

    listaClientes.forEach(c => {
        const option = new Option(`${c.name} - ${c.ci_ruc}`, c.customer_id, false, false);
        option.dataset.name = c.name;
        option.dataset.email = c.email;
        option.dataset.phone = c.phone;
        option.dataset.ruc = c.ci_ruc;
        $select.append(option);
    });

    $select.select2({width: '100%'});

    $select.on("change", function () {
        const selectedOption = this.selectedOptions[0];
        const campos = ["nombre", "email", "telefono", "ruc"];

        if (this.value) {
            campos.forEach(c => {
                document.getElementById(c).value = selectedOption.dataset[c];
                document.getElementById(c).readOnly = true;
            });
        } else {
            campos.forEach(c => {
                document.getElementById(c).value = "";
                document.getElementById(c).readOnly = false;
            });
        }
    });
}

// Se puede continuar agregando cargarVentaExistente y otros handlers si lo necesitas aquí.

// Document Ready

$(document).ready(async function () {
    await cargarProductos();
    await cargarClientes();
    toggleOpcionesCredito();

    $('input[name="tipoPago"]').on("change", toggleOpcionesCredito);

    $("#cuotas").on("input", function () {
        const cantidad = parseInt(this.value);
        const esIrregular = document.getElementById("esIrregular").checked;
        if (!isNaN(cantidad) && cantidad > 0 && esIrregular) {
            generarCamposDias(cantidad);
        } else {
            document.getElementById("diasInputs").innerHTML = "";
        }
    });

    $("#esIrregular").on("change", function () {
        const cantidad = parseInt(document.getElementById("cuotas").value);
        if (this.checked && !isNaN(cantidad) && cantidad > 0) {
            generarCamposDias(cantidad);
        } else {
            document.getElementById("diasInputs").innerHTML = "";
        }
    });

    const tabla = document.querySelector("#tablaProductos tbody");

    tabla.addEventListener("input", e => {
        if (e.target.classList.contains("cantidad") || e.target.classList.contains("precio")) {
            const fila = e.target.closest("tr");
            calcularTotalFila(fila);
        }
    });

    tabla.addEventListener("click", e => {
        if (e.target.classList.contains("eliminarFila")) {
            e.target.closest("tr").remove();
            calcularTotalGeneral();
        }
    });

    $("#btnAgregarFila").on("click", () => {
        const nuevaFila = document.createElement("tr");
        nuevaFila.innerHTML = `
            <td>
                <select class="form-select producto-selector" required style="width: 100%">
                    <option value="">Seleccione Producto</option>
                </select>
            </td>
            <input type="number" class="form-control sale_id" hidden="true"/>
            <td><input type="number" class="form-control precio" required step="0.01" min="0"/></td>
            <td><input type="number" class="form-control cantidad" required value="0" min="0"/></td>
            <td><input type="text" class="form-control total" required readonly data-raw="0"/></td>
            <td class="text-center">
                <button type="button" class="btn btn-danger fa-solid fa-trash eliminarFila"></button>
            </td>`;

        document.querySelector("#tablaProductos tbody").appendChild(nuevaFila);
        actualizarSelects();
    });
});