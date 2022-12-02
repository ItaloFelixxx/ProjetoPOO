package dominio;

public class Fornecedor {
	private String cnpj;
	private String razaoSocial;
	private String logradouro;
	private int numero;
	private String telefone;
	private String email;
	private String complemento;
	private String bairro;
	private String cidade;
	private String estado;
	private String cep;

	public Fornecedor(String cnpj, String razaoSocial,  String logradouro, int numero, String telefone, String email, String complemento, String bairro, String cidade, String estado, String cep) {
		this.cnpj = cnpj;
		this.razaoSocial = razaoSocial;
		this.logradouro = logradouro;
		this.numero = numero;
		this.telefone = telefone;
		this.email = email;
		this.complemento = complemento;
		this.bairro = bairro;
		this.cidade = cidade;
		this.estado = estado;
		this.cep = cep;
	}
	
	public Fornecedor() {
		//Construtor teste para testar
		this.cnpj = "789456";
		this.razaoSocial = "";
		this.logradouro = "";
		this.numero = 0;
		this.telefone = "";
		this.email = "";
		this.complemento = "";
		this.bairro = "";
		this.cidade = "";
		this.estado = "";
		this.cep = "";
	}
	
	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}
	
	
}
