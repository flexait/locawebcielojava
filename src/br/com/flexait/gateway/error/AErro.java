package br.com.flexait.gateway.error;

public abstract class AErro {
	
	abstract String getCodigo();
	
	abstract String getMensagem();
	
	abstract String getDescricao();

	@Override
	public String toString() {
		return "AErro [codigo=" + getCodigo() + ", mensagem="
				+ getMensagem() + ", descricao=" + getDescricao() + "]";
	}
	
}
