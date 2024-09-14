<h2>API REST - Encurtador de Links</h2>
Repositório da API de Encurtador de Links - Desafio da comunidade Backend Brasil

<h4> Esta API, criada usando Java e Springboot, tem a funcionalidade de receber um link via requisição POST, realizar a lógica de encurtamento do link, persistir o link em um banco de dados H2 (que só funciona em tempo de execução) e retornar o link encurtado. Além disso, o link expira após cinco minutos</h4>

<h3>Como usar</h3>
- Usando um aplicativo de testes como Insomnia ou Postman, faça uma requisição POST enviando no corpo um JSON no seguinte formato:
{
    "url": "https://www.exemplo.com"
}

- A requisição irá retornar o código "200 - OK" e o link encurtado no corpo da resposta.

![teste_post_postman](https://github.com/user-attachments/assets/fdf7e932-0144-4ce1-9102-587f3a7c5c79)

- Cole o link retornado na sua requisição GET.
  
![teste_get_postman](https://github.com/user-attachments/assets/0d91a9ec-0a34-4112-8f7d-61a2bd812e46)


<h3>Lógica de encurtamento</h3>
- O encurtamento da url é realizado, na camada de service do projeto (neste caso, na classe UrlShorteningService), usando o objeto RandomStringUtils da biblioteca Apache Commons Lang3 para criar um sequência aleatória de no mínimo 5 e no máximo 10 caracteres, que é atribuída a uma variável (neste caso, "shortUrl"):

```java
private String generateShortUrl(){
    String shortUrl;
    do{
        shortUrl = RandomStringUtils.randomAlphanumeric(5,10);
    }while(repository.findByShortUrl(shortUrl).isPresent());
    return shortUrl;
}
```
