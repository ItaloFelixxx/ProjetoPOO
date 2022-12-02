package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.Cliente;
import dominio.Estoque;
import dominio.Fornecedor;
import dominio.ProdutoVenda;
import dominio.Venda;

public class ProdutosVendaDAO {
	private Conexao c;
	private String REL = "SELECT * FROM Produtos_venda";
	private String INS = "INSERT INTO Produtos_venda(fk_codigo, fk_numero, quantidade, subtotal) VALUES (?,?,?,?)"; 


	public ProdutosVendaDAO() {
		c = new Conexao("jdbc:postgresql://localhost:5432/mercearia","postgres","30042003");
	}

	public void incluir(ProdutoVenda produto, int numero) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(INS);
			instrucao.setInt(1, produto.getCodigo());
			instrucao.setInt(2, numero);
			instrucao.setInt(3, produto.getQuantidade());
			instrucao.setFloat(4, produto.getSubtotal());
			instrucao.execute();
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao inserir produto na venda" + e.getMessage());
		}
	}
	
	
	public ArrayList<ProdutoVenda> relatorio() {
		ProdutoVenda produto;
		Estoque estoque;
		ArrayList<ProdutoVenda> produtos  = new ArrayList<ProdutoVenda>();
		EstoqueDAO eDAO = new EstoqueDAO();
		try {
			c.conectar();
			Statement instrucao = c.getConexao().createStatement();
			ResultSet rs = instrucao.executeQuery(REL);
			while(rs.next()) {
				estoque = eDAO.buscar(rs.getInt("codigo"));
				produto = new ProdutoVenda(estoque.getCodigo(), estoque.getDescricao(), estoque.getPreco(), rs.getInt("qtd"), estoque.getQtdEstoque(), estoque.getF());
				produtos.add(produto);
			}
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao emitir relatorio!" + e.getMessage());
		}
		return produtos;
	}
}
