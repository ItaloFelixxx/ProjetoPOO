package persistencia;

import java.sql.Connection;
import java.sql.DriverManager;

public class Conexao {

	private String caminho;
	private String usuario;
	private String senha;
	private Connection con;
	
	public Conexao() {
		this.caminho = "jdbc:postgresql://localhost:5432/mercearia";
		this.usuario = "postgres";
		this.senha = "30042003";
	}
	
	public void conectar() {
		try {
			Class.forName("org.postgresql.Driver");
			con = DriverManager.getConnection(caminho,usuario,senha);
		}catch(Exception e){
			System.out.println("Erro de conexao"+e.getMessage());
		}
	}
	
	public void desconectar() {
		try {
			con.close();
		}catch(Exception e) {
			System.out.println("Erro na desconexão"+e.getMessage());
		}
	}
	
	public Connection getConexao() {
		return con;
	}
}