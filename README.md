# Spring Boot : Application PetClinic

## Features  

### 1.  Unit Test  --> Tag v1.0.0

mvn test -Dspring.profiles.active=h2

### 2. Pruebas de integración web — Grupo 1

**Integrante C — Iván Jara Ayala.** Actualización y borrado de propietarios.

| Prueba | Resultado esperado |
| --- | --- |
| `testUpdateOwner` | Se crea un propietario, se actualiza con `PUT` y se verifica el cambio con `GET`. |
| `testDeleteOwner` | Se crea un propietario y `DELETE` devuelve `200 OK`. |
| `testDeleteOwnerKO` | El intento de borrar el propietario `1000` devuelve `404 Not Found`. |

Los tres casos están en `OwnerControllerTest` con H2 y en `OwnerControllerMockitoTest` con Mockito.

```bash
./mvnw clean test -Dspring.profiles.active=h2
```
