package br.com.flexait.gateway.error;

public abstract class AErro {
	
	public abstract String getCodigo();
	
	public abstract String getMensagem();
	
	public abstract String getDescricao();

	@Override
	public String toString() {
		return "AErro [codigo=" + getCodigo() + ", mensagem="
				+ getMensagem() + ", descricao=" + getDescricao() + "]";
	}
	
}
