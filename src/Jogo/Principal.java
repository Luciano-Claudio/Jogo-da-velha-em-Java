package Jogo;

import java.util.Scanner;

public class Principal {
	public static Scanner input = new Scanner(System.in);

	public static void main(String[] args) {

		int jogadas = 0;
		int jogador = 1;
		int ganhou = 0;

		int[][] tabuleiro = new int[3][3];
		IniciarTabuleiro(tabuleiro);

		ApagarTela();
		Player p1 = new Player();
		Player p2 = new Player();
		String nome;
		System.out.println("Digite o nome do player 1:");
		nome = input.nextLine();
		IniciarPlayer(p1, nome, 1);
		System.out.println("Digite o nome do player 2:");
		nome = input.nextLine();
		IniciarPlayer(p2, nome, -1);

		while (ganhou == 0 && jogadas < 9) {
			jogador = Jogada(tabuleiro, jogador, p1, p2);

			jogadas++;

			ganhou = checarVitoria(tabuleiro);
		}
		ApagarTela();
		ExibirTabuleiro(tabuleiro);

		if(ganhou == 0)
        	System.out.println("\n\n\t\tDeu VELHA! x_x\n");
		else if(ganhou == 1)
			System.out.printf("\n\n\tParabéns pela vitória %s\n\n",p1.nome);
		else
			System.out.printf("\n\n\tParabéns pela vitória %s\n\n",p2.nome);
		input.close();

	}

	// Funções do Tabuleiro
	public static void IniciarTabuleiro(int[][] tabuleiro) {

		for (int linha = 0; linha < 3; linha++)
			for (int coluna = 0; coluna < 3; coluna++)
				tabuleiro[linha][coluna] = 0;
	}

	public static void ExibirTabuleiro(int[][] tabuleiro, int x, int y, int jogador, Player p1, Player p2) {
		int i = 1;
		System.out.println();
		for (int linha = 0; linha < 3; linha++) {
			System.out.print("\t\t");

			for (int coluna = 0; coluna < 3; coluna++) {

				if (linha == x && coluna == y)
					System.out.print("\033[47m");

				if (tabuleiro[linha][coluna] == -1) {
					System.out.print("\033[31m X ");
				}
				if (tabuleiro[linha][coluna] == 1) {
					System.out.print("\033[34m O ");
				}
				if (tabuleiro[linha][coluna] == 0) {
					System.out.print("   ");
				}

				System.out.print("\033[0m");
				if (coluna == 0 || coluna == 1)
					System.out.print("|");
			}

			System.out.printf("\t\t %d  %d  %d\n", i, i + 1, i + 2);
			i += 3;
			if (linha < 2)
				System.out.println("\t\t--- --- ---");

		}
		// Print Cor da vez
		System.out.printf("\n\t     ");
		if (jogador == 1)
			System.out.printf("\033[34m( %s )", p1.nome);
		else
			System.out.printf("\033[31m( %s )", p2.nome);
		System.out.println("\033[0m\n\tDigite 0 para confirmar a casa!\n\n");
	}

	public static void ExibirTabuleiro(int[][] tabuleiro) {
		System.out.println();
		for (int linha = 0; linha < 3; linha++) {
			System.out.print("\t\t");

			for (int coluna = 0; coluna < 3; coluna++) {

				if (tabuleiro[linha][coluna] == -1) {
					System.out.print("\033[31m X ");
				}
				if (tabuleiro[linha][coluna] == 1) {
					System.out.print("\033[34m O ");
				}
				if (tabuleiro[linha][coluna] == 0) {
					System.out.print("   ");
				}

				System.out.print("\033[0m");
				if (coluna == 0 || coluna == 1)
					System.out.print("|");
			}

			if (linha < 2)
				System.out.println("\n\t\t--- --- ---");
		}
	}

	public static int Jogada(int[][] tabuleiro, int jogador, Player p1, Player p2) {
		int num = 1;
		int l = 0, c = 0;

		// Casa livre
		myloop: for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (tabuleiro[i][j] == 0) {
					l = i;
					c = j;
					break myloop;
				}
			}
		}

		int pos = tabuleiro[l][c];

		do {
			ApagarTela();
			tabuleiro[l][c] = jogador;
			ExibirTabuleiro(tabuleiro, l, c, jogador, p1, p2);
			num = input.nextInt();

			if (num >= 1 && num <= 9) {
				int aux = num - 1;
				if (tabuleiro[aux / 3][aux % 3] == 0) {
					tabuleiro[l][c] = pos;
					l = aux / 3;
					c = aux % 3;
					pos = tabuleiro[l][c];
				}
			}
		} while (num >= 1 && num <= 9);

		jogador *= -1;

		return jogador;
	}

	public static int checarVitoria(int tabuleiro[][]) {
		int l, c, soma;
		// checar se houve vitória por linha
		for (l = 0; l < 3; l++) {
			soma = 0;
			for (c = 0; c < 3; c++)
				soma += tabuleiro[l][c];

			if (soma == 3)
				return 1;
			else if (soma == -3)
				return -1;
		}
		// checar se houve vitória por coluna
		for (c = 0; c < 3; c++) {
			soma = 0;
			for (l = 0; l < 3; l++)
				soma += tabuleiro[l][c];

			if (soma == 3)
				return 1;
			else if (soma == -3)
				return -1;
		}

		// checar se houve vitória pelas diagonais
		soma = 0;
		for (l = 0; l < 3; l++)
			soma += tabuleiro[l][l];
		if (soma == 3)
			return 1;
		else if (soma == -3)
			return -1;

		soma = tabuleiro[0][2] + tabuleiro[1][1] + tabuleiro[2][0];
		if (soma == 3)
			return 1;
		else if (soma == -3)
			return -1;

		return 0;
	}
	

	// Funções do Player
	public static void IniciarPlayer(Player p, String nome, int jogador) {
		p.nome = nome;
		p.jogador = jogador;
	}
	
	// Funções do Console
	public static void ApagarTela() {
		System.out.print("\033[H\033[2J");
		System.out.flush();
	}
}
