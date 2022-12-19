package dominio;

public class ProdutoVenda extends Estoque {
	private int quantidade;
	private float subtotal;

	public ProdutoVenda(int codigo, String descricao, float preco, int quantidade, int qtdEstoque, Fornecedor f) {
		super(codigo, descricao, preco, qtdEstoque, f);
		this.quantidade = quantidade;
		atualizarSubtotal();
	}

	public ProdutoVenda() {
		
	}
	
   public ProdutoVenda(int quantidade, int codigo, float subtotal) {
		super(codigo," ", 0, 0, null );
		this.quantidade = quantidade;
		this.subtotal = subtotal;
	}

	public void atualizarSubtotal() {
		this.subtotal = (quantidade * super.getPreco());
	}

	public int getQuantidade() {
		return quantidade;
	}	
	
	public void setQuantidade(int quantidade) {
		this.quantidade = quantidade;
	}

	public float getSubtotal() {
		return subtotal;
	}
	
}
