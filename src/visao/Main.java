package visao;

import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int op;
		
		do {
			System.out.println("Menu principal");
			System.out.println("1 - Nova venda");
			System.out.println("2 - Aquisição de estoque");
			System.out.println("3 - Relatórios");
			System.out.println("4 - Sair");
			System.out.print("Digite a opção desejada:");
			op = sc.nextInt();
			switch(op) {
			case 1:
				break;
			case 2:
				break;
			case 3:
				break;
			}
		}while(op!=4);
		
	}

}