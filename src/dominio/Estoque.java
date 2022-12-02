package dominio;

public class Estoque {
	private int codigo;
	private String descricao;
	private float preco;
	private int qtdEstoque;
	private Fornecedor f;

	
	public Estoque(int codigo, String descricao, float preco, int qtdEstoque, Fornecedor f) {
		this.codigo = codigo;
		this.descricao = descricao;
		this.preco = preco;
		this.qtdEstoque = qtdEstoque;
		this.f = f;
    }
	
	public Estoque() {
		
	}
	
	
	public Fornecedor getF() {
		return f;
	}

	public void setF(Fornecedor f) {
		this.f = f;
	}

	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}

	public int getQtdEstoque() {
		return qtdEstoque;
	}
	
	public Fornecedor getFornecedor() {
		return f;
	}
	
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	public float getPreco() {
		return preco;
	}
	public void setPreco(float preco) {
		this.preco = preco;
	}
}