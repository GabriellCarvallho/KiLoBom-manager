🍔 Rede Ki Lo Bom

Projeto de Persistência de Objetos usando Java e db4o, modelando o sistema da rede Ki Lo Bom e aplicando operações CRUD, consultas e relacionamentos entre objetos.

🎯 Objetivo

Desenvolver um modelo orientado a objetos com db4o, explorando:
  Relacionamentos N:N entre Cliente e Filial através de Consumo.
  Operações CRUD completas (Criar, Ler, Atualizar, Apagar).
  Consultas utilizando SODA (Simple Object Database Access).

🧩 Tecnologias Utilizadas

Java POO
db4o
SODA (Simple Object Database Access)

🏗 Estrutura do Projeto

O projeto está organizado em pacotes principais:
  Pacote	Descrição
  modelo	Classes de domínio (Cliente, Filial, Consumo, Localizacao)
  util	Conexão e configuração do db4o
  appconsole	Aplicações para CRUD e consultas

  
🔍 Consultas Implementadas

  Consumos realizados em uma determinada data 
  Consumos de um cliente X
  Clientes que consumiram em mais de N filiais

⚙️ Requisitos Importantes
  Cascata configurada em Util.java
  Tratamento de objetos órfãos implementado em Apagar.java
  Chaves imutáveis: CPF para clientes e ID para filiais/consumos

👨‍💻 Autores
  Gabriel Pereira de Carvalho
  Daniel Lucas Alves da Silva
  
Projeto desenvolvido para a disciplina Persistência de Objetos – IFPB
