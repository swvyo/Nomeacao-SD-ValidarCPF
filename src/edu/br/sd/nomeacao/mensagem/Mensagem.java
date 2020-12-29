package edu.br.sd.nomeacao.mensagem;


import java.io.Serializable;

public class Mensagem implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String endereco;
	private String operacao;
	private String servico;
	
	public Mensagem (String operacao) {
		this.setOperacao(operacao);
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String adress) {
		this.endereco = adress;
	}
	
	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}


	public String getServico() {
		return servico;
	}

	public void setServico(String servico) {
		this.servico = servico;
	}
	
	
}