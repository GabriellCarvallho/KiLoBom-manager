package com.rede.kilobom.fachada;

import java.util.List;
import java.util.Optional;

import com.rede.kilobom.modelo.Cliente;
import com.rede.kilobom.modelo.Consumo;
import com.rede.kilobom.modelo.Filial;
import com.rede.kilobom.modelo.Localizacao;
import com.rede.kilobom.repositorio.ClienteRepositorio;
import com.rede.kilobom.repositorio.ConsumoRepositorio;
import com.rede.kilobom.repositorio.FilialRepositorio;

public final class Fachada {
	
	private static ClienteRepositorio clienteRepositorio;
	private static ConsumoRepositorio consumoRepositorio;
	private static FilialRepositorio filialRepositorio;
	
	static {
		clienteRepositorio = new ClienteRepositorio();
		consumoRepositorio = new ConsumoRepositorio();
		filialRepositorio = new FilialRepositorio();
	}
	
	private Fachada() {}
	
	public static void criarFilial(String nome, Localizacao localizacao) throws IllegalArgumentException {
		filialRepositorio.conectar();
		filialRepositorio.begin();
		
		Optional<Filial> optionalFilial = filialRepositorio.buscarPelaLocalizacao(localizacao);
		if (optionalFilial.isPresent()) {
			filialRepositorio.desconectar();
			throw new IllegalArgumentException("Já existe uma filial nessa localização!");
		}
		
		 Filial filial = new Filial(nome, localizacao);
		
		filialRepositorio.persistir(filial);
		filialRepositorio.commit();
		filialRepositorio.desconectar();
		
	}
	
	public static void criarConsumo(String data, String descricao, double valorPago, int idFilial, String cpfCliente) throws IllegalArgumentException {
		consumoRepositorio.conectar();
		consumoRepositorio.begin();
		
		Optional<Filial> optionalFilial = filialRepositorio.ler(idFilial);
		Optional<Cliente> optionalCliente = clienteRepositorio.ler(cpfCliente);
		
		if (!optionalCliente.isPresent() || !optionalFilial.isPresent()) {
			consumoRepositorio.desconectar();
			throw new IllegalArgumentException("Filial ou Cliente não existentes!");
		}
		
		Cliente cliente = optionalCliente.get();
		Filial filial = optionalFilial.get();
		
		Consumo consumo = new Consumo(data, descricao, valorPago);
		
		criarRelaciomentoConsumo(filial, cliente, consumo);
		
		consumoRepositorio.persistir(consumo);
		filialRepositorio.persistir(filial);
		clienteRepositorio.persistir(cliente);
		
		consumoRepositorio.commit();
		consumoRepositorio.desconectar();
		
	}
	
	public static void criarCliente(String cpf, String nome) throws IllegalArgumentException {
		clienteRepositorio.conectar();
		clienteRepositorio.begin();
		
		Optional<Cliente> optionalCliente = clienteRepositorio.ler(cpf);
		if (optionalCliente.isPresent()) {
			clienteRepositorio.desconectar();
			throw new IllegalArgumentException("CPF já cadastrado!");
		}
		
		Cliente cliente = new Cliente(cpf, nome);
		
		clienteRepositorio.persistir(cliente);
		clienteRepositorio.commit();
		clienteRepositorio.desconectar();
		
	}
	
	public static List<Cliente> listarClientes() {
		clienteRepositorio.conectar();
		List<Cliente> resultado = clienteRepositorio.listar();
		clienteRepositorio.desconectar();
		
		return resultado;
	}
	
	public static List<Filial> listarFiliais() {
		filialRepositorio.conectar();
		List<Filial> resultado = filialRepositorio.listar();
		filialRepositorio.desconectar();
		
		return resultado;
	}
	
	public static List<Consumo> listarConsumos() {
		consumoRepositorio.conectar();
		List<Consumo> resultado = consumoRepositorio.listar();
		consumoRepositorio.desconectar();
		
		return resultado;
	}
	
	public static List<Consumo> buscarConsumosData(String data) {
		consumoRepositorio.conectar();
		List<Consumo> resultado = consumoRepositorio.buscarPorData(data);
		consumoRepositorio.desconectar();
		return resultado;
	}
	
	public static List<Consumo> buscarConsumosCliente(String cpf) {
		consumoRepositorio.conectar();
		List<Consumo> resultado = consumoRepositorio.buscarConsumosCliente(cpf);
		consumoRepositorio.desconectar();
		return resultado;
	}
	
	public static List<Cliente> buscarConsumouEmNFiliais(int n) {
		clienteRepositorio.conectar();
		List<Cliente> resultado = clienteRepositorio.buscarConsumouEmNFiliais(n);
		clienteRepositorio.desconectar();
		return resultado;
	}
	
	public static void deletarConsumo(int idConsumo) throws IllegalArgumentException {
	    consumoRepositorio.conectar();
	    consumoRepositorio.begin();
	    
	    Optional<Consumo> optionalConsumo = consumoRepositorio.ler(idConsumo);
	    if (!optionalConsumo.isPresent()) {
	        consumoRepositorio.desconectar();
	        throw new IllegalArgumentException("Consumo não encontrado!");
	    }
	    
	    Consumo consumo = optionalConsumo.get();
	    
	    consumo.getFilial().removerConsumo(consumo);
	    consumo.getCliente().removerConsumo(consumo);
	    
	    filialRepositorio.persistir(consumo.getFilial());
	    clienteRepositorio.persistir(consumo.getCliente());
	    
	    consumoRepositorio.deletar(consumo);
	    
	    consumoRepositorio.commit();
	    consumoRepositorio.desconectar();
	}
	
	public static void deletarCliente(String cpfCliente) throws IllegalArgumentException {
	    clienteRepositorio.conectar();
	    clienteRepositorio.begin();
	    
	    Optional<Cliente> optionalCliente = clienteRepositorio.ler(cpfCliente);
	    if (!optionalCliente.isPresent()) {
	        clienteRepositorio.desconectar();
	        throw new IllegalArgumentException("Cliente não encontrado!");
	    }
	    
	    Cliente cliente = optionalCliente.get();
	    
	    for (Consumo consumo : cliente.getConsumos()) {
	        consumo.getFilial().removerConsumo(consumo);
	        filialRepositorio.persistir(consumo.getFilial());
	        consumoRepositorio.deletar(consumo);
	    }
	    
	    cliente.getConsumos().clear();
	    
	    clienteRepositorio.deletar(cliente);
	    
	    clienteRepositorio.commit();
	    clienteRepositorio.desconectar();
	}

	public static void deletarFilial(int idFilial) {
	    filialRepositorio.conectar();
	    filialRepositorio.begin();
	    
	    Optional<Filial> optionalFilial = filialRepositorio.ler(idFilial);
	    if (!optionalFilial.isPresent()) {
	        filialRepositorio.desconectar();
	        throw new IllegalArgumentException("Filial não encontrada!");
	    }
	    
	    Filial filial = optionalFilial.get();
	    
	    for (Consumo consumo : filial.getConsumos()) {
	        consumo.getCliente().removerConsumo(consumo);
	        clienteRepositorio.persistir(consumo.getCliente());
	        consumoRepositorio.deletar(consumo);
	    }
	    
	    filial.getConsumos().clear();
	    
	    filialRepositorio.deletar(filial);
	    
	    filialRepositorio.commit();
	    filialRepositorio.desconectar();
	}

	public static Cliente localizarCliente(String cpf) {
		clienteRepositorio.conectar();
	    
	    Optional<Cliente> optionalCliente = clienteRepositorio.ler(cpf);
	    if (!optionalCliente.isPresent()) {
	        clienteRepositorio.desconectar();
	        throw new IllegalArgumentException("Cliente não encontrado!");
	    }

		clienteRepositorio.desconectar();
	    
		return optionalCliente.get();
	}

	public static void alterarFoto(String cpf, byte[] foto) throws Exception {
		try {
			clienteRepositorio.conectar();
			clienteRepositorio.begin();
			
			Optional<Cliente> clienteOp = clienteRepositorio.ler(cpf);
			if (clienteOp.isEmpty())
				throw new Exception("alterar foto - cpf inexistente:" + cpf);

			Cliente cliente = clienteOp.get();

			cliente.setFoto(foto);
			clienteRepositorio.commit();

		} catch (Exception e) {
			clienteRepositorio.rollback();
			throw e;
		} finally {
			clienteRepositorio.desconectar();
		}
	}
	
	private static void criarRelaciomentoConsumo(Filial filial, Cliente cliente, Consumo consumo) {
		filial.adicionarConsumo(consumo);
		cliente.adicionarConsumo(consumo);
		
		consumo.setCliente(cliente);
		consumo.setFilial(filial);
	}
	
	
	public static void atualizarCliente(String cpf, String novoNome) throws IllegalArgumentException {
		clienteRepositorio.conectar();
		clienteRepositorio.begin();
		
		Optional<Cliente> optionalCliente = clienteRepositorio.ler(cpf);
		
		if (!optionalCliente.isPresent()) {
			clienteRepositorio.desconectar();
			throw new IllegalArgumentException("Cliente não encontrado");
		}
		
		Cliente cliente = optionalCliente.get();
		cliente.setNome(novoNome);
		
		clienteRepositorio.persistir(cliente);
		clienteRepositorio.commit();
		clienteRepositorio.desconectar();
	}
	
	
	
	public static void atualizarFilial(int id, Localizacao novaLocalizacao) throws IllegalArgumentException  {
		
		filialRepositorio.conectar();
		filialRepositorio.begin();
		
		Optional<Filial> optionalFilial = filialRepositorio.ler(id);
		if (!optionalFilial.isPresent()) {
            filialRepositorio.desconectar();
            throw new IllegalArgumentException("Filial não encontrada!");
        }
        
        Filial filial = optionalFilial.get();
        filial.setLocalizacao(novaLocalizacao);
        
        filialRepositorio.persistir(filial);
        filialRepositorio.commit();
        filialRepositorio.desconectar();
    
		
	}
	public static void atualizarConsumo(int id, String novaData, String novaDescricao, double novoValor) 
            throws IllegalArgumentException {
        consumoRepositorio.conectar();
        consumoRepositorio.begin();
        
        Optional<Consumo> optionalConsumo = consumoRepositorio.ler(id);
        if (!optionalConsumo.isPresent()) {
            consumoRepositorio.desconectar();
            throw new IllegalArgumentException("Consumo não encontrado!");
        }
        
        Consumo consumo = optionalConsumo.get();
        
        consumo.setData(novaData);
        consumo.setDescricao(novaDescricao);
        consumo.setValorpago(novoValor);
        
        consumoRepositorio.persistir(consumo);
        consumoRepositorio.commit();
        consumoRepositorio.desconectar();
        
        
        
	}
	
	
}
