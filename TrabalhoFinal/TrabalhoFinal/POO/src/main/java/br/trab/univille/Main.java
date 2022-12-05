package br.trab.univille;

import br.trab.univille.dao.DAOLista;
import br.trab.univille.dao.DAOTarefa;
import br.trab.univille.service.ListaService;
import br.trab.univille.service.TarefaService;
import br.trab.univille.ui.TelaInicial;

import java.awt.*;

public class Main {
    public static void main(String[] args){
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    TelaInicial frame = new TelaInicial(new ListaService(new DAOLista()), new TarefaService(new DAOTarefa()));
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
