# MicroSigner

O MicroSigner é uma aplicação web desenvolvida como trabalho de Computação Distrubuída na Universidade Federal de Santa Catarina.

O back end da aplicação foi desenvolvido em Java com o framework Spring e foi dividido em dois microsserviços: microsigner-user e microsigner-crypto. O primeiro lida com a gestão dos usuários, enquanto o segundo lida com as operações criptográficas.

Além disso, o front end foi desenvolvido em JavaScript com o framework React.

Por fim, foi utilizado o API Gateway APISIX para orquestrar os microsserviços e o Docker como ferramenta para subir a aplicação.

## Pré-requistos

- Docker >= 24.0.5

## Como executar

Mude o diretório atual para o diretório do docker.
```shell
cd microsigner-docker/
```
Execute o docker compose up.

```shell
docker compose up
```
