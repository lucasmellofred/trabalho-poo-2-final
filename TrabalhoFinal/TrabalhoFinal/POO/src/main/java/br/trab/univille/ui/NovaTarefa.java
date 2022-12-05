package br.trab.univille.ui;

import br.trab.univille.model.Lista;
import br.trab.univille.model.Tarefa;
import br.trab.univille.service.ListaService;
import br.trab.univille.service.TarefaService;
import javax.swing.*;

public class NovaTarefa extends JFrame {
    private final ListaService listaService;
    private final TarefaService tarefaService;
    private Lista lista;

    public NovaTarefa(ListaService listaService, TarefaService tarefaService, Lista lista) {
        this.listaService = listaService;
        this.tarefaService = tarefaService;
        this.lista = lista;
        this.salvarTarefa();
    }

    private void salvarTarefa() {
            Tarefa tarefa = new Tarefa();
            tarefa.setLista(lista);
            tarefaService.adicionarTarefaEmUmaLista(tarefa);
            this.dispose();
        };
}