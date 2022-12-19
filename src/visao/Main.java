package visao;

import java.util.ArrayList;
import java.util.Scanner;

import dominio.Cliente;
import dominio.Estoque;
import dominio.Fornecedor;
import dominio.ProdutoVenda;
import dominio.Venda;
import persistencia.ClienteDAO;
import persistencia.EstoqueDAO;
import persistencia.FornecedorDAO;
import persistencia.ProdutosVendaDAO;
import persistencia.VendaDAO;

public class Main {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int op, op2, op3, op6, numAux, qtdAux, i=0, codAux, qtdEstoque, qtdAuxTotal, posicao, codProduto, numCompra;
		Venda v;
		String stringsAux = new String();
		Cliente c;
		ArrayList<ProdutoVenda> p = null;
		Estoque e;
		ProdutoVenda pv;
		Fornecedor f;
		
		VendaDAO vDao = new VendaDAO();
		EstoqueDAO eDao = new EstoqueDAO();
		ClienteDAO cDao = new ClienteDAO();
		ProdutosVendaDAO pvDao = new ProdutosVendaDAO();
		FornecedorDAO fDao = new FornecedorDAO();
		do {
			System.out.println("Menu principal");
			System.out.println("1 - Nova venda");
			System.out.println("2 - Aquisição de estoque");
			System.out.println("3 - Relatórios");
			System.out.println("4 - Cancelar venda de um produto");   // deletar!!!!
			System.out.println("5 - Alterar");
			System.out.println("6 - Sair");
			System.out.print("Digite a opção desejada:");
			op = sc.nextInt();
			System.out.println();
			switch(op) {
			case 1:
					sc.nextLine(); // absorvendo o enter!
					v = new Venda();
					System.out.println("Menu secundario venda");
					System.out.println("Digite o CPF do cliente: ");
					stringsAux = sc.nextLine();
					c = cDao.buscar(stringsAux);
					if(c == null) {
						System.out.println("Cliente nao cadastrado!");
						System.out.println("Cadastrando cliente...");
						c = new Cliente();
						c.setCpf(stringsAux);
						System.out.println("Digite o nome:");
						c.setNome(sc.nextLine());
						System.out.println("Digite o telefone:");
						c.setTelefone(sc.nextLine());
						System.out.println("Digite o email:");
						c.setEmail(sc.nextLine());
						System.out.println("Dados de endereço");
						System.out.println("Digite o logradouro:");
						c.setLogradouro(sc.nextLine());
						System.out.println("Digite o numero:");
						c.setNumero(sc.nextInt());
						sc.nextLine();
						System.out.println("Digite o complemento:");
						c.setComplemento(sc.nextLine());
						System.out.println("Digite o CEP:");
						c.setCep(sc.nextLine());
						System.out.println("Digite o bairro:");
						c.setBairro(sc.nextLine());
						System.out.println("Digite a cidade:");
						c.setCidade(sc.nextLine());
						System.out.println("Digite o estado:");
						c.setEstado(sc.nextLine());
						cDao.incluir(c);  
						System.out.println("Cliente cadastrado com sucesso! ");
						System.out.println();
					}else {
						System.out.println("Cliente: " + c.getNome() + ", CPF: " + c.getCpf());
					}
					System.out.println("Digite o numero da venda");
					numAux = sc.nextInt();
					if(vDao.buscar(numAux) == null) {
						v.setNumero(numAux);
						v.setCliente(c);
						System.out.println("Digite a data da venda");
						sc.nextLine();  // absorvendo o enter
						stringsAux =  sc.nextLine();	
						v.setDataVenda(stringsAux);
						System.out.println("Digite forma de pagamento e/ou observações: ");
						stringsAux = sc.nextLine();
						v.setObservacoes(stringsAux);
						p = new ArrayList<ProdutoVenda>();
						do {
							System.out.println("Digite o codigo do produto que deseja adicionar");
							e = eDao.buscar(sc.nextInt());
							if(e == null) {
								System.out.println("Produto não existe no estoque!!!");
							}else {
								posicao = -1; //flag -  produto não existe na venda 
								System.out.println("Digite a quantidade que deseja adicionar: ");
								qtdAux = sc.nextInt();
								//calcular a quantidade desejada do produto + a já adicionada a esta venda --> qtdAuxTotal
								qtdAuxTotal = qtdAux;
								for(i=0; i<p.size(); i++) {
									if(p.get(i).getCodigo()==e.getCodigo()) { //verifica se o produto já foi adicionado nessa compra específica
										posicao = i; // o produto foi encontrado na venda na posição i
										qtdAuxTotal += p.get(i).getQuantidade();
									}
								}
								if(qtdAuxTotal <= e.getQtdEstoque()) { //o produto ainda tem estoque disponível
									if(posicao == -1) { //o produto ainda não existe nessa venda
										pv = new ProdutoVenda(e.getCodigo(), e.getDescricao(), e.getPreco(), qtdAux, e.getQtdEstoque(), e.getF());
										p.add(pv);
									}else { //o produto já existe nessa venda
										p.get(posicao).setQuantidade(qtdAuxTotal);	
										p.get(posicao).atualizarSubtotal();
									}
								}else {
									System.out.println("Quantidade indisponível no estoque. Verifique a quantidade digitada e tente novamente!");
									System.out.println();											
								}
							}
							System.out.println("Deseja incluir mais produtos? (1-SIM / 2-NÃO)");
							op2 = sc.nextInt();
						}while(op2!= 2);
						System.out.println("Dados da venda: ");
						System.out.println("Numero: " + v.getNumero());
						System.out.println("Observações: " + v.getObservacoes());
						for(i=0; i< p.size(); i++) {
							System.out.println("Codigo do produto: " + p.get(i).getCodigo());
							System.out.println("Produto: " + p.get(i).getDescricao());
							System.out.println("Quantidade: " + p.get(i).getQuantidade());
						}
						v.setListasProdutos(p);
						v.setTotal();
						System.out.println("Total: " + v.getTotal());
						System.out.println("Deseja concluir a compra? (1-SIM/2-NÃO)");
						if(sc.nextInt() == 1) {
							vDao.incluir(v); //incluir a venda no banco
							System.out.println("Venda concluida!");
							System.out.println();
						}else {
							break;
						}
					}else {
						System.out.println("Numero de venda ja cadastrado!");
					}
				break;
			case 2:
				System.out.println("2 - Aquisição de estoque");
				System.out.println("Digite o codigo do produto");
				codAux = sc.nextInt();
				e = new Estoque();
				if(eDao.buscar(codAux) == null) {
					e.setCodigo(codAux);
					sc.nextLine(); // absorvendo o enter!!!
					System.out.println("Produto não existe no estoque!!!");
					System.out.println("Digite o nome do produto: ");
					e.setDescricao(sc.nextLine());
					System.out.println("Digite o preço do produto: ");
					e.setPreco(sc.nextFloat());
					System.out.println("Digite a quantidade que esta sendo adquirida: ");
					qtdEstoque = sc.nextInt();
					e.setQtdEstoque(qtdEstoque);
					sc.nextLine(); // absorvendo o enter!!!
					System.out.println("");
					System.out.println("Digite o CNPJ do fornecedor: ");
					stringsAux = sc.nextLine();
					f = fDao.buscar(stringsAux); 
					if(f==null) {
						f = new Fornecedor();
						System.out.println("Fornecedor ainda não cadastrado!!!"); // preenchendo novo fornecedor
						f.setCnpj(stringsAux);
						System.out.println("Insisra os dados do fornecedor para cadastro: ");
						System.out.println("Digite a razão social: ");
						f.setRazaoSocial(sc.nextLine());
						System.out.println("Digite o telefone de contato: ");
						f.setTelefone(sc.nextLine());
						System.out.println("Digite o email: ");
						f.setEmail(sc.nextLine());
						System.out.println("Digite o logradouro: ");
						f.setLogradouro(sc.nextLine());
						System.out.println("Digite o numero do imovel: ");
						f.setNumero(sc.nextInt());
						sc.nextLine(); //absorvendo o enter!
						System.out.println("Complemento: ");
						f.setComplemento(sc.nextLine());
						System.out.println("Bairro: ");
						f.setBairro(sc.nextLine());
						System.out.println("Digite a cidade: ");
						f.setEstado(sc.nextLine());
						System.out.println("Digite o estado: ");
						f.setCidade(sc.nextLine());
						System.out.println("Digite o CEP: ");
						f.setCep(sc.nextLine());
						fDao.incluir(f);      // incluindo um novo fornecedor na tabela
					    System.out.println("Fornecedor cadastrado com sucesso!");
					}
						e.setF(f);
						eDao.incluir(e);    // concluindo a inclusao do produto
						System.out.println("Produto incluido com sucesso!");
				}else {
						e = eDao.buscar(codAux);
						System.out.println("Produto encontrado: " + e.getDescricao() + ", codigo: " + e.getCodigo()  + ", quantidade em estoque: " + e.getQtdEstoque());
						System.out.println("Digite a quantidade que esta sendo adquirida: ");
						qtdEstoque = sc.nextInt();
						eDao.entradaEstoque(e.getCodigo(), e.getQtdEstoque(), qtdEstoque);
						e = eDao.buscar(codAux);
						System.out.println("Saldo do estoque atualizado: " + e.getQtdEstoque());
						System.out.println();
				}	
				break;
			case 3:
				do {
						System.out.println("Relatorios!");
						System.out.println("Escolha o relatorio: ");
						System.out.println("1 - Produtos ");
						System.out.println("2 - Clientes ");
						System.out.println("3 - Vendas ");
						System.out.println("4 - Fornecedores ");
						System.out.println("5 - Voltar");
						System.out.print("Digite a opção desejada:");
						op3 = sc.nextInt(); 
						System.out.println();
						switch(op3) {
							case 1:
								ArrayList<Estoque> listaestoque = new ArrayList<Estoque>();
								listaestoque = eDao.relatorio();
								if(listaestoque.size() == 0) {
									System.out.println("Não existem intens no estoque! \n");
									System.out.println();
								}else {
									for(i = 0; i<listaestoque.size(); i++) {
										System.out.println("Código: " + listaestoque.get(i).getCodigo());    
										System.out.println("Descrição: " + listaestoque.get(i).getDescricao());    
										System.out.println("Preço: " + listaestoque.get(i).getPreco());
										System.out.println("Quantidade no estoque: " + listaestoque.get(i).getQtdEstoque());
										System.out.println("Fornecedor: " + listaestoque.get(i).getF().getRazaoSocial());  
										System.out.println();
									}
								}	
							break;
							case 2:
								ArrayList<Cliente> listacliente = new ArrayList<Cliente>();
								listacliente = cDao.relatorio();
								if(listacliente.size() == 0) {
									System.out.println("Não exsitem clientes cadastrados! \n");
									System.out.println();
								}else {
									for(i = 0; i<listacliente.size(); i++) {
										System.out.println("Nome " + listacliente.get(i).getNome());
										System.out.println("CPF: " + listacliente.get(i).getCpf());
										System.out.println("Telefone: " + listacliente.get(i).getCpf());
										System.out.println("Email: " + listacliente.get(i).getEmail());
										System.out.println("Logradouro: " + listacliente.get(i).getLogradouro());
										System.out.println("Numero : " + listacliente.get(i).getNumero());
										System.out.println("CPF: " + listacliente.get(i).getCpf());
										System.out.println("Complemento: " + listacliente.get(i).getComplemento());
										System.out.println("Cidade: " + listacliente.get(i).getCidade());
										System.out.println("Estado: " + listacliente.get(i).getEstado());
										System.out.println("CeP: " + listacliente.get(i).getCep());
										System.out.println();
									}	
								}	
							break;
							case 3: 
								ArrayList<Venda> listavendas = new ArrayList<Venda>();
								listavendas = vDao.relatorio();
								if(listavendas.size() == 0) {
									System.out.println("Não ha vendas realizadas! \n");
									System.out.println();
								}else {
									for(i = 0; i<listavendas.size(); i++) {
										System.out.println("Numero: " + listavendas.get(i).getNumero());
										System.out.println("Data da venda: " + listavendas.get(i).getDataVenda());
										System.out.println("Total: " + listavendas.get(i).getTotal());
										System.out.println("Observações: " + listavendas.get(i).getObservacoes());
										System.out.println("Cliente: " + listavendas.get(i).getCliente().getNome() + ", CPF: " + listavendas.get(i).getCliente().getCpf());
										System.out.println();
									}
								}	
							break;		
							case 4: 
								ArrayList<Fornecedor> listafornecedor = new ArrayList<Fornecedor>();
								listafornecedor = fDao.relatorio();
								if(listafornecedor.size() == 0) {
									System.out.println("Não existem fornecedores cadastrados! \n");
									System.out.println();
								}else {
									for(i = 0; i<listafornecedor.size(); i++) {
										System.out.println("CNPJ: " + listafornecedor.get(i).getCnpj());
										System.out.println("Razão social: " + listafornecedor.get(i).getRazaoSocial());
										System.out.println("Email: " + listafornecedor.get(i).getEmail());
										System.out.println("Logradouro: " + listafornecedor.get(i).getLogradouro());
										System.out.println("Numero : " + listafornecedor.get(i).getNumero());
										System.out.println("Complemento: " + listafornecedor.get(i).getComplemento());
										System.out.println("Cidade: " + listafornecedor.get(i).getCidade());
										System.out.println("Estado: " + listafornecedor.get(i).getEstado());
										System.out.println("CeP: " + listafornecedor.get(i).getCep());
										System.out.println();
									}
								}	
							break;
						}	
				 
				}while(op3 != 5 );	
			break;	
			case 4:
				System.out.println("4 - Deletar produto de uma compra");
				System.out.println("Digite o codigo do produto: ");
				codProduto = sc.nextInt();
				System.out.println("Digite o numero da venda: ");
				numCompra = sc.nextInt();
				pv = pvDao.buscar(codProduto, numCompra);
				if(pv == null) {
					System.out.println("Produto e/ou venda nao foram encontrados!");
				}else {
					e = eDao.buscar(codProduto);
					eDao.entradaEstoque(codProduto, e.getQtdEstoque(), pv.getQuantidade());
					v = vDao.buscar(numCompra);
					vDao.alterarTotal(v.getNumero(), (v.getTotal()-pv.getSubtotal()));
					pvDao.excluir(v.getNumero(), pv.getCodigo());
				}
			break;
			case 5:
				System.out.println("5- Alterar");
				do {
					System.out.println("Escolha o que deseja Alterar: ");
					System.out.println("1 - Produto: ");
					System.out.println("2 - Cliente: ");
					System.out.println("3 - Fornecedor: ");
					System.out.println("4 - Voltar");
					System.out.print("Digite a opção desejada:");
					op6 = sc.nextInt();
					System.out.println();
					switch(op6) {
					case 1: 
						System.out.println("Digite o codigo do produto: ");
						codAux = sc.nextInt();
						if(eDao.buscar(codAux) == null) {
							System.out.println("Produto não existe no estoque!");
						}else {
							e = new Estoque();
							e = eDao.buscar(codAux);
							System.out.println("Produto: " + e.getDescricao() + ", codigo: " + e.getCodigo() + " ");
							System.out.println("Digite o novo codigo do produto: ");
							e.setCodigo(sc.nextInt());
							sc.nextLine(); // absorvendo o enter
							System.out.println("Digite o nome do produto: ");
							e.setDescricao(sc.nextLine());
							System.out.println("Digite o preço do produto: ");
							e.setPreco(sc.nextFloat());
							System.out.println("Digite a quantidade do estoque: ");
							e.setQtdEstoque(sc.nextInt());
							sc.nextLine(); // absorvendo o enter!!!
							eDao.alterar(e, codAux );
							System.out.println("Produto: " + e.getDescricao() + ", codigo: " + e.getCodigo() + ", Alterado com sucesso! ");
						}
					break;
					case 2:
						sc.nextLine(); // aborvendo o enter!!!
						System.out.println("Digite o CPF do cliente: ");
						stringsAux = sc.nextLine();
						if(cDao.buscar(stringsAux) == null) {
							System.out.println("Cliente não Cadastrado!");
						}else {
							c = new Cliente();
							c = cDao.buscar(stringsAux);
							System.out.println("Cliente: " + c.getNome() + ", CPF: " + c.getCpf() + " ");
							System.out.println("Digite o novo CPF: ");
							c.setCpf(sc.nextLine());
							System.out.println("Digite o nome:");
							c.setNome(sc.nextLine());
							System.out.println("Digite o telefone:");
							c.setTelefone(sc.nextLine());
							System.out.println("Digite o email:");
							c.setEmail(sc.nextLine());
							System.out.println("Dados de endereço");
							System.out.println("Digite o logradouro:");
							c.setEstado(sc.nextLine());
							System.out.println("Digite o numero:");
							c.setNumero(sc.nextInt());
							sc.nextLine(); // absorvendo o enter
							System.out.println("Digite o CEP:");
							c.setCep(sc.nextLine());
							System.out.println("Digite o bairro:");
							c.setBairro(sc.nextLine());
							System.out.println("Digite a cidade:");
							c.setCidade(sc.nextLine());
							System.out.println("Digite o estado:");
							c.setEstado(sc.nextLine());
							cDao.alterar(c, stringsAux);
							System.out.println("Cliente: " + c.getNome() + ", CPF: " + c.getCpf() + ", Alterado com sucesso! ");	
						}
					break;
					case 3:
						sc.nextLine(); // aborvendo o enter!!!
						System.out.println("Digite o CNPJ do fornecedor: ");
						stringsAux = sc.nextLine();
						if(fDao.buscar(stringsAux) == null) {
							System.out.println("Fornecedor não Cadastrado!");
						}else {
							f = new Fornecedor();
							f = fDao.buscar(stringsAux);
							System.out.println("Fornecedor: " + f.getRazaoSocial() + ", CNPJ: " + f.getCnpj() + " ");
							System.out.println("Digite o novo CNPJ");
							f.setCnpj(sc.nextLine());
							System.out.println("Digite a razão social: ");
							f.setRazaoSocial(sc.nextLine());
							System.out.println("Digite o logradouro: ");
							f.setLogradouro(sc.nextLine());
							System.out.println("Digite o numero do imovel: ");
							f.setNumero(sc.nextInt());
							sc.nextLine(); //absorvendo o enter!
							System.out.println("Complemento: ");
							f.setComplemento(sc.nextLine());
							System.out.println("Digite o telefone de contato: ");
							f.setTelefone(sc.nextLine());
							System.out.println("Digite o email: ");
							f.setEmail(sc.nextLine());
							System.out.println("Digite a cidade: ");
							f.setEstado(sc.nextLine());
							System.out.println("Digite o estado: ");
							f.setCidade(sc.nextLine());
							System.out.println("Digite o CEP: ");
							f.setCep(sc.nextLine());
							fDao.alterar(f, stringsAux);
							System.out.println("Fornecedor: " + f.getRazaoSocial() + ", CNPJ: " + f.getCnpj() + " alterado com sucesso! ");
						}
						break;
					}
				}while(op6 != 4);
			}			
		}while(op!= 6);
		sc.close();
	}

}