package controle;

import Model.Tarefa;
import Model.TarefaPrioritaria;
import Excessoes.EntradaInvalidaExcessao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import repository.TarefaRepository;

public class TaskManager {
    private ArrayList<Tarefa> tarefas;
    private TarefaRepository repository;
    private Scanner scanner;

    public TaskManager() {
        this.tarefas = new ArrayList<>();
        this.repository = new TarefaRepository();
        this.scanner = new Scanner(System.in);
    }

    public void carregarTarefasDoBanco() {
        try {
            this.tarefas = repository.listar();
        } catch (SQLException e) {
            System.out.println("Erro ao carregar as tarefas do banco de dados.");
            e.printStackTrace();
        }
    }

    public void adicionarTarefa() {
        System.out.println("\n=== NOVA TAREFA ===");
        
        try {
            String titulo = lerString("Titulo: ");
            String descricao = lerString("Descricao: ");
            
            System.out.print("Esta tarefa e prioritaria? (S/N): ");
            String opcao = scanner.nextLine().trim().toUpperCase();
            
            Tarefa novaTarefa;
            
            if (opcao.equals("S")) {
                int prioridade = lerPrioridade();
                String prazo = lerString("Prazo (ex: 10/12/2024): ");
                novaTarefa = new TarefaPrioritaria(titulo, descricao, prioridade, prazo);
            } else {
                novaTarefa = new Tarefa(titulo, descricao);
            }
            
        
            repository.salvar(novaTarefa);
            
            
            tarefas.add(novaTarefa);
            
            System.out.println("Tarefa criada com sucesso!");
            System.out.println("ID no Banco: " + novaTarefa.getId());
            
        } catch (EntradaInvalidaExcessao e) {
            System.out.println("Erro de entrada: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("Erro ao salvar no banco de dados.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    public void editarTarefa() {
        System.out.println("\n=== EDITAR TAREFA ===");
        
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada no sistema.");
            return;
        }
        
        listarTarefas();
        
        try {
            System.out.print("\nDigite o ID da tarefa que deseja editar: ");
            int id = scanner.nextInt();
            scanner.nextLine(); 
            
            Tarefa tarefa = buscarTarefaPorId(id);
            
            System.out.println("\n--- Digite os novos dados ---");
            System.out.println("(Pressione ENTER sem digitar nada para manter o valor atual)");
            
            System.out.print("Novo Titulo (Atual: " + tarefa.getTitulo() + "): ");
            String novoTitulo = scanner.nextLine().trim();
            if (novoTitulo.isEmpty()) {
                novoTitulo = tarefa.getTitulo();
            }
            
            System.out.print("Nova Descricao (Atual: " + tarefa.getDescricao() + "): ");
            String novaDescricao = scanner.nextLine().trim();
            if (novaDescricao.isEmpty()) {
                novaDescricao = tarefa.getDescricao();
            }
            
        
            repository.editarTarefa(id, novoTitulo, novaDescricao, tarefa.isConcluida());
            
            tarefa.setTitulo(novoTitulo);
            tarefa.setDescricao(novaDescricao);
            
            System.out.println("\n✅ Tarefa editada com sucesso no banco de dados!");
            
        } catch (EntradaInvalidaExcessao e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            System.out.println("Erro: O ID deve ser um número inteiro.");
        } catch (SQLException e) {
            System.out.println("Erro ao editar no banco de dados.");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    private int lerPrioridade() throws EntradaInvalidaExcessao {
        System.out.println("\nPrioridade:");
        System.out.println("1 - ALTA");
        System.out.println("2 - MÉDIA");
        System.out.println("3 - BAIXA");
        System.out.print("Escolha (1-3): ");
        
        try {
            int prioridade = scanner.nextInt();
            scanner.nextLine(); 

            if (prioridade < 1 || prioridade > 3) {
                throw new EntradaInvalidaExcessao("Prioridade deve ser entre 1 e 3");
            }
            return prioridade;
            
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            throw new EntradaInvalidaExcessao("Entrada inválida. Digite um número entre 1 e 3.");
        }
    }

    private String lerString(String mensagem) {
        System.out.print(mensagem);
        return scanner.nextLine().trim();
    }

    public void listarTarefas() {
        System.out.println("\n=== LISTA DE TAREFAS ===");
        
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
            return;
        }
        
       
        for (Tarefa t : tarefas) {
            System.out.println("\n[ID: " + t.getId() + "] " + t);
        }
        
        System.out.println("\nTotal de tarefas: " + tarefas.size());
    }

    public void concluirTarefa() {
        System.out.println("\n=== MARCAR TAREFA COMO CONCLUÍDA ===");
        
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa para concluir.");
            return;
        }
        
        listarTarefas();
        
        try {
            System.out.print("\nDigite o ID da tarefa a ser concluída: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            Tarefa tarefa = buscarTarefaPorId(id);
            
            if (tarefa.isConcluida()) {
                System.out.println("Esta tarefa já está concluída!");
                return;
            }
            
            
            repository.atualizarStatus(id, true);
            
           
            tarefa.setConcluida(true);
            System.out.println("Tarefa \"" + tarefa.getTitulo() + "\" concluída com sucesso!");
            
        } catch (EntradaInvalidaExcessao e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            scanner.nextLine();
            System.out.println("Erro: Entrada invalida. Digite um número.");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    public void removerTarefa() {
        System.out.println("\n=== REMOVER TAREFA ===");
        
        if (tarefas.isEmpty()) {
            System.out.println("Nenhuma tarefa para remover.");
            return;
        }
        
        listarTarefas();
        
        try {
            System.out.print("\nDigite o ID da tarefa a ser removida: ");
            int id = scanner.nextInt();
            scanner.nextLine();
            
            Tarefa tarefa = buscarTarefaPorId(id);
            
          
            repository.deletar(id);
            
            
            tarefas.remove(tarefa);
            System.out.println("Tarefa \"" + tarefa.getTitulo() + "\" removida com sucesso!");
            
        } catch (EntradaInvalidaExcessao e) {
            System.out.println("Erro: " + e.getMessage());
        } catch (InputMismatchException e) {
            scanner.nextLine(); 
            System.out.println("Erro: Entrada inválida. Digite um número.");
        } catch (Exception e) {
            System.out.println("Erro inesperado: " + e.getMessage());
        }
    }

    
    private Tarefa buscarTarefaPorId(int id) throws EntradaInvalidaExcessao {
        for (Tarefa t : tarefas) {
            if (t.getId() == id) {
                return t;
            }
        }
        throw new EntradaInvalidaExcessao("ID inválido. Tarefa não encontrada.");
    }

    public void fechar() {
        scanner.close();
    }
}