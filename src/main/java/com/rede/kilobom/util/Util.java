package com.rede.kilobom.util;

import java.io.IOException;
import java.util.Properties;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public final class Util {

	private static EntityManager manager;
	private static EntityManagerFactory factory;

	public static EntityManager conectar() {

		if (manager != null) 
			return manager;
		
		try {

			Properties config = new Properties();
			config.load(Util.class.getClassLoader().getResourceAsStream("config.properties"));

			String sgbd = config.getProperty("sgbd");
			String banco = config.getProperty("banco");
			String ip = config.getProperty("ipatual");
			String usuario = config.getProperty("usuario");
			String senha = config.getProperty("senha");


			Properties properties = new Properties();

			switch (sgbd) {
				case "postgresql" -> {
					properties.setProperty("jakarta.persistence.jdbc.driver", "org.postgresql.Driver");
					properties.setProperty("jakarta.persistence.jdbc.url", "jdbc:postgresql://" + ip + ":5432/" + banco);
					properties.setProperty("jakarta.persistence.jdbc.user", usuario);
					properties.setProperty("jakarta.persistence.jdbc.password", senha);
				}

				case "mysql" -> {
					properties.setProperty("jakarta.persistence.jdbc.driver", "com.mysql.cj.jdbc.Driver");
					properties.setProperty("jakarta.persistence.jdbc.url", "jdbc:mysql://" + ip + ":3306/" + banco + "?createDatabaseIfNotExist=true");
					properties.setProperty("jakarta.persistence.jdbc.user",usuario);
					properties.setProperty("jakarta.persistence.jdbc.password", senha);
				}
			
				default -> {
					System.out.println("SGBD não configurado!");
				}
			}

			String unitName = "hibernate" + "-" + sgbd;
			factory = Persistence.createEntityManagerFactory(unitName, properties);
			manager = factory.createEntityManager();

			return manager;
			
		} catch (IOException e) {
			System.out.println("Erro ao ler configurações");
			throw new RuntimeException("Erro ao conectar no banco: " + e.getCause());
		}

	}

	public static void desconectar() {
		if (manager != null && manager.isOpen()) {
			manager.close();
			factory.close();
			manager = null;
		}
	}
	
	public static EntityManager getManager() {
		return manager;
	}


}
