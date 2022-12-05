package br.trab.univille.ui;

import br.trab.univille.model.Lista;
import br.trab.univille.service.ListaService;
import br.trab.univille.service.TarefaService;
import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class TelaInicial extends JFrame {
    private final ListaService listaService;
    private final TarefaService tarefaService;

    public TelaInicial(ListaService listaService, TarefaService tarefaService) {
        this.listaService = listaService;
        this.tarefaService = tarefaService;

        setTitle("Lista de Tarefas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        setSize(650, 500);

        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        setVisible(true);

        ArrayList<Lista> listas = listaService.listarTodasAsListas();
        List<Object> listObjects = UtilsMethods.newJTableByLista(listas, listaService, tarefaService,this);

        JTable table = (JTable) listObjects.get(0);
        JScrollPane scrollPane = (JScrollPane) listObjects.get(1);

        table.setBounds(30, 50, 540, 300);

        this.btnAdicionarUmaLista();
        this.labelJtable();
    }

    public void labelJtable() {
        JLabel ID = new JLabel("ID");
        JLabel Titulo = new JLabel("Título");
        JLabel Data = new JLabel("Data");
        JLabel Finalizado = new JLabel("Finalizado");
        JLabel Prioridade = new JLabel("Prioridade");
        JLabel Excluir = new JLabel("Excluir tarefa");

        ID.setBounds(50, 25, 50, 35);
        Titulo.setBounds(127, 25, 50, 35);
        Data.setBounds(203, 25, 150, 35);
        Finalizado.setBounds(280, 25, 150, 35);
        Prioridade.setBounds(357,25,150,35);
        Excluir.setBounds(513,25,150,35);

        this.add(ID);
        this.add(Titulo);
        this.add(Data);
        this.add(Finalizado);
        this.add(Prioridade);
        this.add(Excluir);
    }

    public void btnAdicionarUmaLista(){
        JButton btnNewLista = UtilsMethods.newWindowPanel(200, 390, 20, 200, "Criar nova tarefa", this, new NovaLista(listaService, tarefaService));
    }
}