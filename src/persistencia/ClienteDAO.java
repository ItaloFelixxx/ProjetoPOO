package persistencia;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

import dominio.Cliente;

public class ClienteDAO {
	private Conexao c;
	private String REL = "SELECT * FROM cliente";
	private String BUS = "SELECT * FROM cliente WHERE cpf = ?";
	private String INS = "INSERT INTO cliente(cpf,nome, telefone, email, logradouro,numero, complemento, estado ,cidade ,bairro , cep) VALUES (?,?,?,?,?,?,?,?,?,?,?) "; 
	private String DEL = "DELETE FROM cliente WHERE cpf = ?";
	private String ALT = "UPDATE cliente cpf = ?, nome = ?, telefone = ?, email = ?, logradouro = ?,numero = ?, complemento = ?, estado = ?,cidade = ?,bairro = ?, cep = ? where cpf = ?";
	
	public ClienteDAO() {
		c = new Conexao("jdbc:postgresql://localhost:5432/mercearia","postgres","30042003");
	}
	
	public Cliente buscar(String cpf) {
		Cliente cliente = null;
		 try {
			 c.conectar();
			 PreparedStatement instrucao = c.getConexao().prepareStatement(BUS);
			 instrucao.setString(1, cpf);
			 ResultSet rs = instrucao.executeQuery();
			 if(rs.next()) {
				 cliente = new Cliente(rs.getString("cpf"), rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("logradouro"), rs.getString("complemento"), rs.getString("estado"), rs.getString("cidade"), rs.getString("bairro"), rs.getString("cep"), rs.getInt("numero"));
			 }
			 c.desconectar();
			 
		 }catch(Exception e) {
			 System.out.println("Erro na busca"+e.getMessage());
		 }
		 return cliente;
	}

	public void alterar(Cliente cliente) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(ALT);
			instrucao.setString(1, cliente.getCpf());
			instrucao.setString(2, cliente.getNome());
			instrucao.setString(3, cliente.getTelefone());
			instrucao.setString(4, cliente.getEmail());
			instrucao.setString(5, cliente.getLogradouro());
			instrucao.setInt(6, cliente.getNumero());
			instrucao.setString(7, cliente.getComplemento());
			instrucao.setString(8, cliente.getEstado());
			instrucao.setString(9, cliente.getCidade());
			instrucao.setString(10, cliente.getBairro());
			instrucao.setString(11, cliente.getCep());
			instrucao.setString(12, cliente.getCpf());
			instrucao.execute();
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro na alteração"+e. getMessage());
		}
	}
	
	public void incluir(Cliente cliente) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(INS);
			instrucao.setString(1, cliente.getCpf());
			instrucao.setString(2, cliente.getNome());
			instrucao.setString(3, cliente.getTelefone());
			instrucao.setString(4, cliente.getEmail());
			instrucao.setString(5, cliente.getLogradouro());
			instrucao.setInt(6, cliente.getNumero());
			instrucao.setString(7, cliente.getComplemento());
			instrucao.setString(8, cliente.getEstado());
			instrucao.setString(9, cliente.getCidade());
			instrucao.setString(10, cliente.getBairro());
			instrucao.setString(11, cliente.getCep());
			instrucao.execute();
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao inserir Cliente" + e.getMessage());
		}
	}
	
	public void excluir(String cpf) {
		try {
			c.conectar();
			PreparedStatement instrucao = c.getConexao().prepareStatement(DEL);
			instrucao.setString(1, cpf);
			instrucao.execute();
			c.desconectar();
		}catch(Exception e) {
			System.out.println("Erro na exclusão"+e.getMessage());
		}
	}

	public ArrayList<Cliente> relatorio() {
		Cliente cliente;
		ArrayList<Cliente> listaClientes = new ArrayList<Cliente>();
		try {
			c.conectar();
			Statement instrucao = c.getConexao().createStatement();
			ResultSet rs = instrucao.executeQuery(REL);
			while(rs.next()) {
				cliente = new Cliente(rs.getString("cpf"), rs.getString("nome"), rs.getString("telefone"), rs.getString("email"), rs.getString("logradouro"), rs.getString("complemento"), rs.getString("estado"), rs.getString("cidade"), rs.getString("bairro"), rs.getString("cep"), rs.getInt("numero"));
				listaClientes.add(cliente);
			}
			c.desconectar();
		}catch(Exception e){
			System.out.println("Erro ao emitir relatorio!" + e.getMessage());
		}
		return listaClientes;
	}
	
	
	
}