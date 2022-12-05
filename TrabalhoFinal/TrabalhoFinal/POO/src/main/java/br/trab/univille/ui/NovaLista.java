package br.trab.univille.ui;

import br.trab.univille.model.Lista;
import br.trab.univille.service.ListaService;
import br.trab.univille.service.TarefaService;
import javax.swing.*;

public class NovaLista extends JFrame {
    private final ListaService listaService;
    private final TarefaService tarefaService;
    private JTextArea TituloTarefa;
    private JCheckBox Finalizar;
    private JButton SalvarTarefa;

    public NovaLista(ListaService listaService, TarefaService tarefaService) {

        this.listaService = listaService;
        this.tarefaService = tarefaService;

        setTitle("Cadastro de Tarefas");
        setSize(500, 250);
        setLayout(null);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        this.TituloTarefa = UtilsMethods.newJtextArea(50, 50, 75, 400, "Titulo", this);
        this.Finalizar = UtilsMethods.newCheckBox(50, 150, 10, 15, "Tarefa Finalizada", this);
        this.SalvarTarefa = UtilsMethods.newBtn(230, 150, 20, 200, "Cadastrar Tarefa", this);
        this.salvarLista();
    }

    private void salvarLista() {
        SalvarTarefa.addActionListener(e -> {
            Lista lista = new Lista();
            lista.setTitulo(TituloTarefa.getText());
            lista.setExcluida(Finalizar.isSelected());
            listaService.criarLista(lista);
            this.dispose();
            new TelaInicial(listaService, tarefaService);
        });
    }
}