# JEMPE Admin — Panel Administrativo

> Sistema de gestión para el **Club JEMPE de Patinaje de Velocidad**, Quito, Ecuador.  
> Asignatura: Desarrollo de Aplicaciones Web · UDLA · Semestre VI  
> Autor: **Miguel Ángel Peña Rodríguez**

---

## 📋 Descripción

Aplicación **Spring Boot MVC** que administra las entidades core del proyecto JEMPE:
atletas, entrenadores, categorías, disciplinas, padres/tutores y sesiones de entrenamiento.

### Funcionalidades principales

| Módulo | Descripción |
|---|---|
| 🏷️ Categorías | CRUD de categorías por rango de edad |
| 🎯 Disciplinas | CRUD vinculado a categoría (dropdown en formulario) |
| 👨‍🏫 Entrenadores | CRUD con validación de **cédula ecuatoriana (Módulo 10)** en back-end |
| 👪 Padres / Tutores | CRUD con validación de **cédula ecuatoriana (Módulo 10)** en back-end |
| ⛸️ Atletas | CRUD con **dropdown en cascada** Categoría → Disciplina y validación de cédula |
| 📋 Sesiones | CRUD con asignación de atletas mediante checkboxes (sin FK manual) |

### Validaciones back-end (dato sensible)

`CedulaValidator` implementa el **algoritmo Módulo 10** oficial del Registro Civil Ecuatoriano:

1. Exactamente 10 dígitos numéricos  
2. Provincia válida (`01-24` o `30`)  
3. Tercer dígito < 6 (persona natural)  
4. Suma ponderada con coeficientes `[2,1,2,1,2,1,2,1,2]`; si resultado ≥ 10 restar 9  
5. Dígito verificador = `10 - (suma % 10)`, con `suma % 10 == 0 → 0`

Se invoca en `AtletaService`, `EntrenadorService` y `PadreTutorService` antes de cada persistencia.

### Dropdowns en cascada (sin FK manual)

- **Atleta → Nueva sesión:** el usuario elige **Categoría**, AJAX filtra las **Disciplinas** disponibles  
  (`GET /api/disciplinas?categoriaId=X`)
- **Dashboard sesiones:** `Entrenador → Sesiones → Atletas asignados` vía  
  `GET /api/sesiones-por-entrenador` y `GET /api/atletas-por-sesion`

---

## 🛠️ Stack Tecnológico

| Capa | Tecnología |
|---|---|
| Lenguaje | Java 17 |
| Framework | Spring Boot 3.2.5 |
| Presentación | Spring MVC + Thymeleaf 3 |
| Persistencia | Spring Data JPA + Hibernate |
| Seguridad | Spring Security 6 (in-memory) |
| DB desarrollo | H2 In-Memory (`MODE=MSSQLServer`) |
| DB producción | SQL Server / Azure SQL |
| Build | Maven 3 |
| Extras | Lombok, Jakarta Validation |

---

## 🚀 Ejecución local

### Requisitos

- Java 17+
- Maven 3.8+

### Pasos

```bash
# 1. Clonar el repositorio
git clone https://github.com/<tu-usuario>/jempe-admin.git
cd jempe-admin

# 2. Compilar
mvn clean package -DskipTests

# 3. Ejecutar
mvn spring-boot:run
```

La aplicación arranca en **http://localhost:8080**

### Credenciales de acceso (demo)

| Usuario | Contraseña | Rol |
|---|---|---|
| `admin` | `jempe2024` | Administrador |
| `alexis` | `jempe2024` | Entrenador |

### Consola H2 (solo desarrollo)

```
URL: http://localhost:8080/h2-console
JDBC URL: jdbc:h2:mem:jempedb
Usuario: sa  |  Contraseña: (vacío)
```

---

## 📁 Estructura del proyecto

```
src/
├── main/
│   ├── java/com/jempe/admin/
│   │   ├── config/
│   │   │   ├── SecurityConfig.java       ← Spring Security in-memory
│   │   │   └── DataInitializer.java      ← Seed de datos demo (perfil dev)
│   │   ├── controller/
│   │   │   ├── HomeController.java
│   │   │   ├── LoginController.java
│   │   │   ├── CategoriaController.java
│   │   │   ├── DisciplinaController.java
│   │   │   ├── EntrenadorController.java
│   │   │   ├── PadreTutorController.java
│   │   │   ├── AtletaController.java
│   │   │   ├── SesionController.java
│   │   │   └── ApiController.java        ← REST endpoints para AJAX
│   │   ├── entity/                       ← Entidades JPA
│   │   ├── repository/                   ← Spring Data JPA repositories
│   │   ├── service/                      ← Lógica de negocio + validaciones
│   │   └── util/
│   │       └── CedulaValidator.java      ← Algoritmo Módulo 10
│   └── resources/
│       ├── application.properties
│       ├── static/
│       │   ├── css/styles.css
│       │   └── js/cascading.js           ← AJAX para dropdowns en cascada
│       └── templates/
│           ├── login.html
│           ├── dashboard.html
│           ├── layout/base.html          ← Layout compartido (sidebar + topbar)
│           ├── categoria/
│           ├── disciplina/
│           ├── entrenador/
│           ├── padre/
│           ├── atleta/
│           └── sesion/
```

---

## 🔒 Seguridad

- Autenticación con formulario propio (`/login`)
- Dos usuarios in-memory con contraseñas **BCrypt**
- Todas las rutas protegidas excepto `/login`, `/css/**`, `/js/**`
- H2 Console habilitada solo en desarrollo (frame options relajadas)

---

## 🧪 Datos de demo

Al arrancar en perfil `default` o `dev`, `DataInitializer` crea automáticamente:

- 3 Categorías (Sub-10, Sub-16, Elite)  
- 5 Disciplinas (300m Sprint, 500m, 1000m, 5000m Puntos, Maratón)  
- 1 Entrenador (Alexis Pérez)  
- 1 Padre/Tutor (Carlos Torres)  
- 3 Atletas (Sofía Mendoza, Diego Ruiz, Valentina López)  
- 1 Sesión planificada con 2 atletas asignados

---

## 📜 Licencia

Proyecto académico — UDLA · 2024  
Uso educativo y de referencia.
