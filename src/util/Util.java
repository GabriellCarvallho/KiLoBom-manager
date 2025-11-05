package util;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;

import modelo.Cliente;
import modelo.Consumo;
import modelo.Filial;
import modelo.Localizacao;

public final class Util {

	private static final String DB_FILE = "banco.db4o";
	private static ObjectContainer manager;
	
	public static ObjectContainer conectar() {
		if (manager != null) return manager;
		
		EmbeddedConfiguration config = Db4oEmbedded.newConfiguration();
		config.common().messageLevel(0);
		
		config.common().objectClass(Cliente.class).cascadeOnUpdate(true);
        config.common().objectClass(Filial.class).cascadeOnUpdate(true);
        config.common().objectClass(Consumo.class).cascadeOnUpdate(true);
        config.common().objectClass(Consumo.class).cascadeOnDelete(true);
        config.common().objectClass(Localizacao.class).cascadeOnUpdate(true);
		
		manager = Db4oEmbedded.openFile(config, DB_FILE);
		ControleID.ativar(true, manager);
		return manager;
	}
	
	public static void desconectar() {
		if (manager != null) {
			manager.close();
			manager = null;
		}
	}

}
