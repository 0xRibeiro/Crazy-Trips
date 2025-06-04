document.addEventListener("DOMContentLoaded", function () {
    fetch("http://localhost:8080/viagens")
        .then((response) => response.json())
        .then((data) => {
            const tabelaViagens = document.getElementById("viagens");

            data.forEach((viagem) => {
                const row = document.createElement("tr");

                row.innerHTML = `
                  <td>${viagem.id}</td>
                  <td>${viagem.nome}</td>
                  <td>${viagem.cpf}</td>
                  <td>${viagem.telefone}</td>
                  <td>${viagem.dataNascimento}</td>
                  <td>${viagem.cep}</td>
                  <td>${viagem.origem}</td>
                  <td>${viagem.destino}</td>
                  <td>${viagem.dataPartida}</td>
                  <td>${viagem.dataChegada}</td>
                  <td>R$ ${viagem.preco.toFixed(2)}</td>
                  <td>${viagem.tipo || '-'}</td>
                  <td>
                    <button class="edit-btn" onclick="editarViagem(${viagem.id})">Editar</button>
                    <button class="delete-btn" onclick="deletarViagem(${viagem.id})">Excluir</button>
                  </td>
                `;

                tabelaViagens.appendChild(row);
            });
        })
        .catch((error) => console.error("Erro ao carregar viagens:", error));
});

function editarViagem(id) {
    window.location.href = `editar.html?id=${id}`;
}

function deletarViagem(id) {
    fetch(`http://localhost:8080/viagens/delete/${id}`, {
        method: "DELETE",
    })
        .then((response) => {
            if (response.ok) {
                alert("Viagem excluída com sucesso!");
                location.reload();
            } else {
                alert("Erro ao excluir a viagem.");
            }
        })
        .catch((error) => console.error("Erro ao excluir viagem:", error));
}
