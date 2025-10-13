🍔 Rede Ki Lo Bom

Projeto de Persistência de Objetos usando Java e db4o, com foco em modelar o sistema da rede Ki Lo Bom e aplicar CRUD, consultas e relacionamentos entre objetos.

🎯 Objetivo

Implementar um modelo orientado a objetos com db4o, explorando:

Relacionamentos N:N entre Cliente e Filial via Consumo;

Operações CRUD completas;

Consultas SODA.

🧩 Tecnologias

Java

db4o

SODA (Simple Object Database Access)

🏗 Estrutura do Projeto

Pacotes principais:

modelo → classes de domínio (Cliente, Filial, Consumo, Localizacao)

util → conexão e configuração do db4o

appconsole → aplicações CRUD e consultas

🔍 Consultas Implementadas

Consumos na data X

Consumos do cliente X

Clientes que consumiram em mais de N filiais

⚙️ Requisitos Importantes

Cascata configurada no Util.java

Tratamento de objetos órfãos em Apagar.java

Chaves imutáveis (cpf, id)

👨‍💻 Autores

Gabriel Pereira de Carvalho
Daniel Lucas Alves da Silva 
Projeto da disciplina de Persistência de Objetos – IFPB