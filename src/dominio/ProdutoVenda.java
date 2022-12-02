package dominio;

public class ProdutoVenda extends Estoque {
	private int quantidade;
	private float subtotal;

	public ProdutoVenda(int codigo, String descricao, float preco, int quantidade, int qtdEstoque, Fornecedor f) {
		super(codigo, descricao, preco, qtdEstoque, f);
		this.quantidade = quantidade;
		this.subtotal = (quantidade * preco);
	}
	
	public int getQuantidade() {
		return quantidade;
	}

	public float getSubtotal() {
		return subtotal;
	}
	
}
