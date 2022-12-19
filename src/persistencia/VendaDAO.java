package persistencia;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.Cliente;
import dominio.ProdutoVenda;
import dominio.Venda;

public class VendaDAO {
	private Conexao c;
	private String REL = "SELECT * FROM venda";
	private String BUS = "SELECT * FROM venda WHERE numero = ?";
	private String INS = "INSERT INTO venda(numero, data_venda,total, observacoes, fk_cpf) VALUES (?,?,?,?,?) "; 
	private String ALT = "UPDATE venda set numero = ?, total = ?, data_venda = ?, observacoes =?, fk_cpf = ? where numero = ?";
	private String ALTtotal = "UPDATE venda set total = ? WHERE numero = ?";
	
	public VendaDAO() {
		c = new Conexao();
	}
	
	public void alterarTotal(int numero, float total) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(ALTtotal);
			instrucao.setFloat(1, total);
			instrucao.setInt(2, numero);
			instrucao.execute();
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao atualizar total " + e.getMessage());
		}
	}
	
	public Venda buscar(int numero) {
		Venda venda = null;
		Cliente cliente = null;
		ArrayList<ProdutoVenda> produtos = null;
		ClienteDAO cDAO = new ClienteDAO();
	    ProdutosVendaDAO pvDAO = new ProdutosVendaDAO();
		 try {
			 c.conectar();
			 PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
			 instrucao.setInt(1, numero);
			 ResultSet rs = instrucao.executeQuery();
			 produtos = new ArrayList<ProdutoVenda>();
			 if(rs.next()) {
				 cliente = cDAO.buscar(rs.getString("fk_cpf"));
				 produtos = pvDAO.relatorio();
				 venda = new Venda(rs.getInt("numero"), rs.getString("data_venda"), cliente, rs.getString("observacoes"), produtos, rs.getFloat("total"));
			 }
			 c.desconectar();
			 
		 }catch(Exception e) {
			 System.out.println("Erro na busca"+e.getMessage());
		 }
		 return venda;
	}
	
	public void incluir(Venda venda) {
		int i;
	    ProdutosVendaDAO pvDAO = new ProdutosVendaDAO();
	    EstoqueDAO eDAO = new EstoqueDAO();
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(INS);
			instrucao.setInt(1, venda.getNumero());
			instrucao.setString(2, venda.getDataVenda());
			instrucao.setFloat(3, venda.getTotal());
			instrucao.setString(4, venda.getObservacoes());
			instrucao.setString(5, venda.getCliente().getCpf());
			instrucao.execute();
			for(i=0; i<venda.getListaDeProdutos().size(); i++) {
				pvDAO.incluir(venda.getListaDeProdutos().get(i), venda.getNumero());
				eDAO.saidaEstoque(venda.getListaDeProdutos().get(i).getCodigo(), venda.getListaDeProdutos().get(i).getQtdEstoque(), venda.getListaDeProdutos().get(i).getQuantidade());
			}	
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao inserir venda no sistema" + e.getMessage());
		}
	}
	
	public void alterar(Venda venda, int novoNumero) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(ALT);
			instrucao.setInt(1, venda.getNumero());
			instrucao.setString(2, venda.getDataVenda());
			instrucao.setFloat(3, venda.getTotal());
			instrucao.setString(4, venda.getObservacoes());
			instrucao.setString(5, venda.getCliente().getCpf());
			instrucao.setInt(6, novoNumero);

			instrucao.execute();
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao alterar dados da venda " + e.getMessage());
		}
	}
	
	public ArrayList<Venda> relatorio() {
		Venda venda  = null;
		Cliente cliente;
		ClienteDAO cDAO = new ClienteDAO();
		ProdutosVendaDAO pvDAO = new ProdutosVendaDAO();
		ArrayList<Venda> listaVenda = new ArrayList<Venda>();
		ArrayList<ProdutoVenda> produtos = new ArrayList<ProdutoVenda>();

		try {
			c.conectar();
			Statement instrucao = c.getConexao().createStatement();
			ResultSet rs = instrucao.executeQuery(REL);
			while(rs.next()) {
				cliente = cDAO.buscar(rs.getString("fk_cpf"));
				produtos = pvDAO.relatorio();
				venda = new Venda(rs.getInt("numero"), rs.getString("data_venda"), cliente, rs.getString("observacoes"), produtos, rs.getFloat("total"));
				listaVenda.add(venda);
			}
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao emitir relatorio!" + e.getMessage());
		}
		return listaVenda;
	}
	
} 