package dominio;

import java.util.ArrayList;
import java.util.Date;

public class Venda {
	private int numero;
	private Cliente c;
	private float total;
	private Date dataVenda;
	private String observacoes;
	private ArrayList<ProdutoVenda> produtos;
	
	public Venda(int numero, Date dataVenda, Cliente c, String observacoes, ArrayList<ProdutoVenda> produtos) {
		this.c = c; 
		this.numero = numero;
		this.dataVenda = dataVenda;
		this.produtos = produtos;
		this.observacoes = observacoes;
		total = 0;
	}
	
	public int getNumero() {
		return numero;
	}
	
	public void setNumero(int numero) {
		this.numero = numero;
	}
	
	public float getTotal() {
		return total;
	}
	public String getObservacoes() {
		return observacoes;
	}
	
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
	public void setTotal() {
		int i;
		for(i=0; i < produtos.size(); i++) {
			this.total = this.total + produtos.get(i).getSubtotal();
		}
	}
	
	public Cliente getCliente() {
		return c;
	}
	
	public Date getDataVenda() { // fazer conversao de tipo para date!
		return dataVenda;
	}
	
	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
	}
	
	public void setListasProdutos(ArrayList<ProdutoVenda> listaProd) {
		this.produtos = listaProd;
	}
	 
	public ArrayList<ProdutoVenda> getListaDeProdutos(){
		return produtos;
	}
	
	public int getTamanhoLista() {
		return produtos.size();
	}
}
