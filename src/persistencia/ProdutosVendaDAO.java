package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.Estoque;
import dominio.ProdutoVenda;

public class ProdutosVendaDAO {
	private Conexao c;
	private String REL = "SELECT * FROM Produtos_venda";
	private String INS = "INSERT INTO Produtos_venda(fk_codigo, fk_numero, quantidade, subtotal) VALUES (?,?,?,?)"; 
	private String DEL = "DELETE FROM Produtos_venda WHERE fk_codigo = ? AND fk_numero = ? ";
	private String BUS = "SELECT * FROM Produtos_venda WHERE fk_codigo = ? AND fk_numero = ?";


	public ProdutosVendaDAO() {
		c = new Conexao();
	}
	
	public ProdutoVenda buscar(int fk_codigo, int fk_numero) {
		ProdutoVenda pv = null;
		 try {
			 c.conectar();
			 PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
			 instrucao.setInt(1, fk_codigo);
			 instrucao.setInt(2, fk_numero);
			 ResultSet rs = instrucao.executeQuery();
			 if(rs.next()) {
				 pv = new ProdutoVenda(rs.getInt("quantidade"), rs.getInt("fk_codigo"), rs.getFloat("subtotal"));
			 }
			 c.desconectar();
			 
		 }catch(Exception e) {
			 System.out.println("Erro na busca"+e.getMessage());
		 }
		 return pv;
	}
	
	public void excluir(int fk_codigo, int fk_numero) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(DEL);
			instrucao.setInt(1, fk_codigo);
			instrucao.setInt(2, fk_numero);
			instrucao.execute();
			c.desconectar();
		}catch(Exception e) {
			System.out.println("Erro na exclusão"+e.getMessage());
		}
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
				estoque = eDAO.buscar(rs.getInt("fk_codigo"));
				produto = new ProdutoVenda(estoque.getCodigo(), estoque.getDescricao(), estoque.getPreco(), rs.getInt("quantidade"), estoque.getQtdEstoque(), estoque.getF());
				produtos.add(produto);
			}
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao emitir relatorio!" + e.getMessage());
		}
		return produtos;
	}
}
