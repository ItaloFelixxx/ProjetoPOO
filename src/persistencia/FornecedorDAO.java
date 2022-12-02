package persistencia;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


import dominio.Fornecedor;

public class FornecedorDAO {
	private String BUS = "SELECT * FROM fornecedor WHERE cnpj = ?";
	private String REL = "SELECT * FROM fornecedor";
	private String INS = "INSERT INTO fornecedor(cnpj, razao_social,logradouro, numero, telefone, email, complemento, bairro, estado, cidade, cep) VALUES (?,?,?,?,?,?,?,?,?,?,?) "; 
	private String DEL = "DELETE FROM fornecedor WHERE cnpj = ?";
	private String ALT = "UPDATE fornecedor cnpj = ?, razao_social = ?, logradouro = ?, numero = ?, telefone = ?, email = ?, complemento = ?, bairro = ?, cidade = ?, estado = ?, cep = ? where cnpj = ?";
	private Conexao c;
	
	public FornecedorDAO() {
		c = new Conexao("jdbc:postgresql://localhost:5432/mercearia","postgres","30042003");
	}
	
	public Fornecedor buscar(String cnpj) {
		Fornecedor fornecedor = null;
		 try {
			 c.conectar();
			 PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
			 instrucao.setString(1, cnpj);
			 ResultSet rs = instrucao.executeQuery();
			 if(rs.next()) {
				 fornecedor = new Fornecedor(rs.getString("cnpj"),rs.getString("razao_social"),rs.getString("logradouro"),rs.getInt("numero"),rs.getString("telefone"),rs.getString("email"),rs.getString("complemento"),rs.getString("bairro"),rs.getString("cidade"),rs.getString("estado"),rs.getString("cep"));
			 }
			 c.desconectar();
			 
		 }catch(Exception e) {
			 System.out.println("Erro na busca"+e.getMessage());
		 }
		 return fornecedor;
	}
	
	public void alterar(Fornecedor fornecedor) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(ALT);
			instrucao.setString(1, fornecedor.getCnpj());
			instrucao.setString(2, fornecedor.getRazaoSocial());
			instrucao.setString(3, fornecedor.getLogradouro());
			instrucao.setInt(4, fornecedor.getNumero());
			instrucao.setString(5, fornecedor.getTelefone());
			instrucao.setString(6, fornecedor.getEmail());
			instrucao.setString(7, fornecedor.getComplemento());
			instrucao.setString(8, fornecedor.getBairro());
			instrucao.setString(9, fornecedor.getCidade());
			instrucao.setString(10, fornecedor.getEstado());
			instrucao.setString(11, fornecedor.getCep());
			instrucao.setString(12, fornecedor.getCnpj());
			instrucao.execute();
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro na alteração"+e. getMessage());
		}
	}
	
	public ArrayList<Fornecedor> relatorio() {
		Fornecedor fornecedor;
		ArrayList<Fornecedor> listaFornecedores = new ArrayList<Fornecedor>();
		try {
			c.conectar();
			Statement instrucao = c.getConexao().createStatement();
			ResultSet rs = instrucao.executeQuery(REL);
			while(rs.next()) {
				fornecedor = new Fornecedor(rs.getString("cnpj"), rs.getString("razao_social"),rs.getString("logradouro"),rs.getInt("numero"), rs.getString("telefone"), rs.getString("email"), rs.getString("complemento"), rs.getString("bairro"), rs.getString("cidade"), rs.getString("estado"), rs.getString("cep"));
				listaFornecedores.add(fornecedor);
			}
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao emitir relatorio!" + e.getMessage());
		}
		return listaFornecedores;
	}
	
	public void incluir(Fornecedor fornecedor) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(INS);
			instrucao.setString(1, fornecedor.getCnpj());
			instrucao.setString(2, fornecedor.getRazaoSocial());
			instrucao.setString(3, fornecedor.getLogradouro());
			instrucao.setInt(4, fornecedor.getNumero());
			instrucao.setString(5, fornecedor.getTelefone());
			instrucao.setString(6, fornecedor.getEmail());
			instrucao.setString(7, fornecedor.getComplemento());
			instrucao.setString(8, fornecedor.getBairro());
			instrucao.setString(9, fornecedor.getCidade());
			instrucao.setString(10, fornecedor.getEstado());
			instrucao.setString(11, fornecedor.getCep());
			instrucao.execute();
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao inserir Fornecedor" + e.getMessage());
		}
	}
	
	public void excluir(String cnpj) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(DEL);
			instrucao.setString(1, cnpj);
			instrucao.execute();
			c.desconectar();
		}catch(Exception e) {
			System.out.println("Erro na exclusão"+e.getMessage());
		}
	}

}