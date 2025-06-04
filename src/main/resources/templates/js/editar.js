document.addEventListener("DOMContentLoaded", function () {
    const params = new URLSearchParams(window.location.search);
    const id = params.get("id");

    if (id) {
        fetch(`http://localhost:8080/viagens/${id}`)
            .then((response) => response.json())
            .then((data) => {
                document.getElementsByName("id")[0].value = data.id;
                document.getElementsByName("nome")[0].value = data.nome;
                document.getElementsByName("nascimento")[0].value = data.nascimento;
                document.getElementsByName("cpf")[0].value = data.cpf;
                document.getElementsByName("telefone")[0].value = data.telefone;
                document.getElementsByName("email")[0].value = data.email;
                document.getElementsByName("cep")[0].value = data.cep;
                document.getElementsByName("destino")[0].value = data.destino;
                document.getElementsByName("origem")[0].value = data.origem;
                document.getElementsByName("partida")[0].value = data.partida;
                document.getElementsByName("chegada")[0].value = data.chegada;
                document.getElementsByName("preco")[0].value = data.preco;


                const tiposSelecionados = data.tipo || [];
                tiposSelecionados.forEach(tipo => {
                    const checkbox = document.querySelector(`input[name="tipo"][value="${tipo}"]`);
                    if (checkbox) {
                        checkbox.checked = true;
                    }
                });

                document.getElementsByName("cliente")[0].value = data.cliente;
            })
            .catch((error) => console.error("Erro ao buscar dados do cliente:", error));
    } else {
        console.error("ID não fornecido na URL");
    }

    document.getElementById("edit-form").addEventListener("submit", function (event) {
        event.preventDefault();

        const tiposSelecionados = Array.from(document.querySelectorAll('input[name="tipo"]:checked'))
            .map(cb => cb.value);

        const clienteAtualizado = {
            id: document.getElementsByName("id")[0].value,
            nome: document.getElementsByName("nome")[0].value,
            nascimento: document.getElementsByName("nascimento")[0].value,
            cpf: document.getElementsByName("cpf")[0].value,
            telefone: document.getElementsByName("telefone")[0].value,
            email: document.getElementsByName("email")[0].value,
            cep: document.getElementsByName("cep")[0].value,
            destino: document.getElementsByName("destino")[0].value,
            origem: document.getElementsByName("origem")[0].value,
            partida: document.getElementsByName("partida")[0].value,
            chegada: document.getElementsByName("chegada")[0].value,
            preco: document.getElementsByName("preco")[0].value,
            tipo: tiposSelecionados,
            cliente: document.getElementsByName("cliente")[0].value
        };

        fetch("http://localhost:8080/viagens/update", {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
            },
            body: JSON.stringify(clienteAtualizado),
        })
            .then((response) => {
                if (response.ok) {
                    alert("Atualizado com sucesso!");
                    window.location.href = "ListarTodos.html";
                } else {
                    alert("Erro ao atualizar.");
                }
            })
            .catch((error) => console.error("Erro ao efetuar a atualização:", error));
    });
});
