# Capitole Test

## Manual de instalación
Este es un manual de instrucciones para ejecutar el API de la prueba tecnica de capitole:

1- Se debe descargar el proyecto de github en un directorio local.

2- Se debe ubicar en la raíz del proyecto donde se descargó y ejecutar el comando de maven: mvn spring-boot:run, en caso 
de tener un IDE se puede ejecutar directamente la aplicación con el archivo EcommerceApplication.

3- El proyecto se ejecutará en el puerto 4300.

4- Para consultar el servicio 1 se debe ejecutar el siguiente comando: 

curl --location 'localhost:4300/shopping-car/calculate-total' \
--header 'Content-Type: application/json' \
--data '[
{
"id":1,
"name":"Cocacola",
"value":2000
},
{
"id":2,
"name":"Pepsi",
"value":3000
},
{
"id":3,
"name":"Colombiana",
"value":4000
},
{
"id":4,
"name":"Postobon",
"value":5000
}
]'

5- Para consultar el servicio 2 se debe ejecutar el siguiente comando:

curl --location 'localhost:4300/shopping-car/upload' \
--form 'file=@"./capitole-test/testFiles/file1.txt"'

## Preguntas


* Escalabilidad y rendimiento: Considera cómo tu API puede ser diseñada para escalar horizontalmente bajo condiciones de alta carga. ¿Se puede desacoplar alguna de las operaciones? ¿Es recomendable usar un patrón de eventos para la funcionalidad de carga de CSV?

  R/:
  - La API puede escalar horizontalmente si se cuenta con una infraestructura escalable como kubernetes donde permita su crecimiento horizontal.
  - Ambas operaciones se pueden desacoplar del proyecto de acuerdo a las necesidades o criticidad del requerimiento, en caso que se requiera las operaciones se pueden separar por controladores o en caso extremo por proyecto.
  - Es posible utilizar un patron de evento siempre y cuando no se requiera una respuesta sincrona como en el caso de de devolver el total de item y la sumatoria. P
    Para trabajar con patrón eventos es valido recordar que es ascincrono y no se puede procesar todo inmediatamente sino cuando el evento lo procese ascincronamente.


* Seguridad: Se debe demostrar cómo gestionarías el almacenamiento seguro de credenciales dentro de la API. ¿Qué prácticas recomiendas para proteger los endpoints y evitar vulnerabilidades comunes (por ejemplo, autenticación, autorización, cifrado de datos sensibles)?
  
  R/:
  - Las credenciales de deben almacenar fuera del servicio en un administrador de secret como un vault o secret management en los servicio de nube.
  - Para proteger los endpoint se lo mínimo que se debe implementar es la autenticación y autorización con JWT o se puede utilizar algún servicio de gestor de identidad (IAM).
  - Los cifrados de datos se pueden implementar en la BD encriptando los datos y en transito en la capa de transporte con TLS.
  - Además los servicio se pueden proteger con ratelimit a los clientes consumidores para evitar denegación, así cómo también un WAF para evitar ataques externos.