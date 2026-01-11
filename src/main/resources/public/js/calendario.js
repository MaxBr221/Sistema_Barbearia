let horarioSelecionado = null;

function carregarHorarios() {
  const data = document.getElementById("data").value;
  const container = document.getElementById("horarios");

  container.innerHTML = "";
  horarioSelecionado = null;

  if (!data) return;

  const diaSemana = new Date(data + "T00:00").getDay();

  if (diaSemana === 0) {
    container.innerHTML = "<p style='grid-column: 1/-1; text-align: center;'>❌ Barbearia fechada neste dia</p>";
    return;
  }

  fetch(`/agendamentos/ocupados?data=${data}`)
    .then(res => res.json())
    .then(ocupados => {
      const horarios = gerarHorarios();
      montarHorarios(horarios, ocupados);
    });
}

function gerarHorarios() {
  const horarios = [];

  for (let h = 9; h < 12; h++) {
    horarios.push(formataHora(h, 0));
    horarios.push(formataHora(h, 30));
  }

  for (let h = 15; h < 18; h++) {
    horarios.push(formataHora(h, 0));
    horarios.push(formataHora(h, 30));
  }

  return horarios;
}

function montarHorarios(horarios, ocupados) {
  const container = document.getElementById("horarios");
  container.innerHTML = "";
  // Ensure the container has the grid class if not already in HTML (but it should be)
  container.classList.add("time-grid");

  horarios.forEach(hora => {
    // If occupied, do not render the button (hide it)
    if (ocupados.includes(hora)) {
      return; 
    }

    const botao = document.createElement("button");
    botao.type = "button";
    botao.innerText = hora;
    botao.classList.add("time-btn"); // Add the main design class

    botao.onclick = () => selecionarHorario(hora, botao);
    container.appendChild(botao);
  });
}

function selecionarHorario(hora, botaoSelecionado) {
  horarioSelecionado = hora;
  document.getElementById("hora").value = hora;

  document
    .querySelectorAll("#horarios button")
    .forEach(btn => btn.classList.remove("selected"));

  botaoSelecionado.classList.add("selected");
}

function confirmarAgendamento() {
  const data = document.getElementById("data").value;
  const hora = document.getElementById("hora").value;

  if (!data || !hora) {
    alert("Selecione a data e o horário");
    return;
  }

  const checkboxes = document.querySelectorAll('input[name="servicos"]:checked');
  if (checkboxes.length === 0) {
    alert("Selecione pelo menos um serviço.");
    return;
  }

  document.getElementById("formAgendamento").submit();
}


function formataHora(hora, minuto) {
  return `${String(hora).padStart(2, "0")}:${minuto === 0 ? "00" : "30"}`;
}
