package br.trab.univille.service;

import br.trab.univille.dao.DAOTarefa;
import br.trab.univille.model.Tarefa;
import javax.swing.*;
import java.time.LocalDate;

public class TarefaService {
    private final DAOTarefa tarefaDao;

    public TarefaService (DAOTarefa tarefaDao) {
        this.tarefaDao = tarefaDao;
    }

    public void adicionarTarefaEmUmaLista(Tarefa tarefa) {
        if (tarefa.getTitulo().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Por favor, digitre um título para a tarefa");
            throw new IllegalArgumentException("O título da tarefa não pode estar vazio");
        }

        if(tarefa.isConcluida()){
            tarefa.setDataConclusao(LocalDate.now());
        }
        tarefa.setId(tarefaDao.getProximoId());
        tarefaDao.create(tarefa);
    }
}