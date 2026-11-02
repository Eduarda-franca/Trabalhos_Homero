package app;

import java.util.InputMismatchException;
import java.util.Scanner;
import database.DatabaseInicializar;
import controle.TaskManager;

public class MainConsole {
    

    public static void main(String[] args) {
        
        DatabaseInicializar.incializar();

        TaskManager taskManager = new TaskManager();
        taskManager.carregarTarefasDoBanco(); 
        Scanner scanner = new Scanner(System.in);
        boolean executando = true;
        
        System.out.println("=======================================");
        System.out.println("   GERENCIADOR DE TAREFAS - TASKMANAGER");
        System.out.println("=======================================");
        
        while (executando) {
            try {
                exibirMenu();
                int opcao = lerOpcao(scanner);
                switch (opcao) {
                    case 1:
                        taskManager.adicionarTarefa();
                        break;
                    case 2:
                        taskManager.listarTarefas();
                        break;
                    case 3:
                        taskManager.concluirTarefa();
                        break;
                    case 4:
                        taskManager.editarTarefa(); 
                        break;
                    case 5:
                        taskManager.removerTarefa(); 
                        break;
                    case 6:
                        System.out.println("\nSaindo do sistema");
                        executando = false;
                        break;
                    default:
                        System.out.println("Opção inválida! Escolha entre 1 e 6.");
                }
                
                if (executando) {
                    System.out.println("\nPressione ENTER para continuar...");
                    scanner.nextLine();
                }
                
            } catch (InputMismatchException e) {
                System.out.println("Erro: Entrada invalida. Digite um numero.");
                scanner.nextLine();
            } catch (Exception e) {
                System.out.println("Erro inesperado: " + e.getMessage());
            }
        }
        
        taskManager.fechar();
        scanner.close();
        System.out.println("\n=======================================");
        System.out.println("   OBRIGADO POR USAR O TASKMANAGER!");
        System.out.println("=======================================");
    }
    

   private static void exibirMenu() {
        System.out.println("\n=======================================");
        System.out.println("  ===== GERENCIADOR DE TAREFAS =====");
        System.out.println("=======================================");
        System.out.println(" 1. Criar nova tarefa");
        System.out.println(" 2. Listar tarefas");
        System.out.println(" 3. Marcar tarefa como concluida");
        System.out.println(" 4. Editar Tarefa");           
        System.out.println(" 5. Remover Tarefa");          
        System.out.println(" 6. Sair");                   
        System.out.println("=======================================");
        System.out.print(" Escolha uma opcao: ");
    }
    
    private static int lerOpcao(Scanner scanner) {
        return scanner.nextInt();
    }
}