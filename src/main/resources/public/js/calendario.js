let horarioSelecionado = null;

function carregarHorarios() {
  const data = document.getElementById("data").value;
  const container = document.getElementById("horarios");

  container.innerHTML = "";
  horarioSelecionado = null;

  if (!data) return;

  const diaSemana = new Date(data + "T00:00").getDay();

  if (diaSemana === 0 || diaSemana === 1) {
    container.innerHTML = "<p>❌ Barbearia fechada neste dia</p>";
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


  for (let h = 15; h < 19; h ++) {
    horarios.push(formataHora(h, 0));
    horarios.push(formataHora(h, 30));
  }

  return horarios;
}

function montarHorarios(horarios, ocupados) {
  const container = document.getElementById("horarios");
  container.innerHTML = "";

  horarios.forEach(hora => {
    const botao = document.createElement("button");
    botao.type = "button";
    botao.innerText = hora;


    if (ocupados.includes(hora)) {
      botao.disabled = true;
      botao.classList.add("ocupado");
    } else {
      botao.onclick = () => selecionarHorario(hora, botao);
    }

    container.appendChild(botao);
  });
}

function selecionarHorario(hora, botaoSelecionado) {
  horarioSelecionado = hora;
  document.getElementById("hora").value = hora;


  document
    .querySelectorAll("#horarios button")
    .forEach(btn => btn.classList.remove("selecionado"));

  botaoSelecionado.classList.add("selecionado");
}

function confirmarAgendamento() {
  const data = document.getElementById("data").value;
  const hora = document.getElementById("hora").value;

  if (!data || !hora) {
    alert("Selecione a data e o horário");
    return;
  }

  document.getElementById("formAgendamento").submit();
}


function formataHora(hora, minuto) {
  return `${String(hora).padStart(2, "0")}:${minuto === 0 ? "00" : "30"}`;
}
