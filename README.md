### Foro Hub Challenge
Foro Hub es una plataforma diseñada para fomentar la comunicación y las discusiones entre usuarios a través de un foro interactivo. Los participantes pueden crear, responder y participar activamente en distintos tópicos.

Funcionalidades principales
Autenticación y registro de usuarios.
Gestión de tópicos: creación, edición y eliminación.
Participación en debates mediante respuestas a tópicos.
Listado de usuarios y tópicos disponibles.
Seguridad basada en autenticación JWT.
Tecnologías empleadas
El proyecto está desarrollado con las siguientes tecnologías:

Lenguaje y Frameworks:
Java 17
Spring Boot
Spring Security
Seguridad: JSON Web Tokens (JWT)
Persistencia: JPA con bases de datos H2 (para desarrollo y pruebas) y MySQL (en producción).
Gestión de dependencias: Maven
Organización del proyecto
El código fuente se encuentra estructurado en las siguientes capas:

Entities: Representan las tablas de la base de datos como clases de entidad.
Dto: Objetos para transferir datos entre el servidor y el cliente.
Repository: Interfaces basadas en JpaRepository para operaciones CRUD.
Service: Contienen la lógica de negocio principal.
Controller: Manejan las solicitudes HTTP y las respuestas.
Security: Configuraciones y clases para la autenticación y seguridad.
