package br.com.mkacunha.persistencia;

import br.com.mkacunha.utils.EclipseLinkUtil;

public class EclipseLinkPersistencia extends JPAPersistencia {

	public EclipseLinkPersistencia() {
		super(EclipseLinkUtil.getEntityManagerFactory());
	}
}
