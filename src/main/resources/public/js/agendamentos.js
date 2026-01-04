document.addEventListener('DOMContentLoaded', () => {
  console.log("meus-agendamentos.js carregou");

  const tabela = document.querySelector('.tabela');
  if (!tabela) return;

  tabela.addEventListener('click', async (event) => {
    if (!event.target.classList.contains('cancel-btn')) return;

    const agendamentoId = event.target.dataset.id;

    if (!confirm('Tem certeza que quer cancelar esse agendamento?')) return;

    try {
      const response = await fetch(`/agendamentos/${agendamentoId}`, {
        method: 'DELETE'
      });

      if (response.ok) {
        alert('Agendamento cancelado!');
        event.target.closest('tr').remove();
      } else {
        alert('Erro ao cancelar o agendamento.');
      }
    } catch (e) {
      console.error(e);
      alert('Erro ao falar com o servidor.');
    }
  });
});
