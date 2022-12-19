package dominio;

import java.util.ArrayList;

public class Venda {
	private int numero;
	private Cliente c;
	private float total;
	private String dataVenda;
	private String observacoes;
	private ArrayList<ProdutoVenda> produtos;
	
	public Venda(int numero, String dataVenda, Cliente c, String observacoes, ArrayList<ProdutoVenda> produtos, float total) {
		this.c = c; 
		this.numero = numero;
		this.dataVenda = dataVenda;
		this.produtos = produtos;
		this.observacoes = observacoes;
		this.total = total;
		
	}
	
	public Venda() {
		
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
	
	public void setCliente(Cliente c) {
		this.c = c;
	}
	
	public Cliente getCliente() {
		return c;
	}
	
	public String getDataVenda() { 
		return dataVenda;
	}
	
	public void setDataVenda(String dataVenda) {
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
	
	public void setTotal() {
		for(int i=0; i<produtos.size();i++) {
			this.total += produtos.get(i).getSubtotal();
		}
	}
}
