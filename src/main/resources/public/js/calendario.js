
async function carregarHorarios(){
    const data  = document.getElementById("data").value;
    if(!data) return;

    horariosSelecionado = null;

    const horarios = gerarHorarios();
    const ocupados = await buscarHorariosOcupados(data);

    montarHorarios(horarios, ocupados);

}

function montarHorarios(horarios, ocupados){
    const container = document.getElementById("horarios");
    container.innerHTML = "";

    horarios.forEach(hora => {
        const botao = document.createElement("button");
        botao.innerHTML = hora;

        if(ocupados.includes(hora)){
            botao.disabled = true;
            botao.classList.add("ocupado");
        }else{
            botao.onclick = () => selecionarHorario(hora, botao);
        }

        container.appendChild(botao);
    });
}
function selecionarHorario(hora, botaoSelecionado){
    horariosSelecionado = hora;

    document.querySelectorAll(".horarios button")
        .forEach(btn => btn.classList.remove("selecionado"));
    botaoSelecionado.classList.add("selecionado");
}

function confirmarAgendamento(){
    const data = document.getElementById("data").value;

    if(!data || !horariosSelecionado){
        alert("selecione a data e o horário");
        return;

    }

    console.log("agendado:", data, horariosSelecionado);

    alert('agendamento confirmado para ${data} ás ${horariosSelecionado}');
}

function formataHora(hora, minuto){
    return `${String(hora).padStart(2, '0')}:${minuto === 0 ? '00': '30'}`;
}