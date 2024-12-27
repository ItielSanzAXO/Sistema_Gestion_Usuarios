# Sistema Gestión Usuarios

Este proyecto es una aplicación de gestión de usuarios desarrollada en Java utilizando Swing para la interfaz gráfica y PostgreSQL como base de datos. La aplicación permite gestionar información de alumnos y profesores, incluyendo la creación, modificación y eliminación de registros.

## 📋 Requisitos

- Java JDK 8 o superior
- PostgreSQL 9.6 o superior
- NetBeans IDE (opcional, pero recomendado)

## 🛠️ Configuración de la Base de Datos

La base de datos se encuentra en una máquina Linux con PostgreSQL. Asegúrate de tener acceso a la base de datos y de configurar las credenciales adecuadamente en el archivo de conexión.

1. Instala PostgreSQL en tu máquina Linux.
2. Crea una base de datos llamada `itiz`.
3. Configura las credenciales de acceso en el archivo de conexión (`CRUD/src/Datos/Conexion.java`).

```java
public static Connection realizaConexion() {
    Connection c = null;
    try {
        Class.forName("org.postgresql.Driver");
        c = DriverManager.getConnection("jdbc:postgresql://<IP_DE_TU_SERVIDOR>:5432/itiz", "postgres", "password");
        System.out.println("Conectado a POSTGRESQL");
    } catch (SQLException | ClassNotFoundException e) {
        System.out.println("Error: " + e);
    }
    return c;
}
```

## 📂 Estructura del Proyecto
El proyecto está organizado en los siguientes paquetes:

- **Alumno**: Contiene las clases relacionadas con la gestión de alumnos.
- **Profesor**: Contiene las clases relacionadas con la gestión de profesores.
- **Control**: Contiene las clases de lógica de negocio y validación.
- **Datos**: Contiene las clases de acceso a datos y conexión a la base de datos.
- **crud**: Contiene las clases principales y la interfaz gráfica.

## 🚀 Funcionalidades

### Gestión de Alumnos
- **Crear Alumno**: Permite crear un nuevo registro de alumno.
- **Modificar Alumno**: Permite modificar la información de un alumno existente.
- **Eliminar Alumno**: Permite eliminar un registro de alumno.
- **Buscar Alumno**: Permite buscar alumnos por nombre o apellidos.

### Gestión de Profesores
- **Crear Profesor**: Permite crear un nuevo registro de profesor.
- **Modificar Profesor**: Permite modificar la información de un profesor existente.
- **Eliminar Profesor**: Permite eliminar un registro de profesor.
- **Buscar Profesor**: Permite buscar profesores por nombre o apellidos.

## ▶️ Ejecución del Proyecto
1. Clona el repositorio en tu máquina local.
2. Abre el proyecto en tu IDE (NetBeans recomendado).
3. Configura la conexión a la base de datos en `Conexion.java`.
4. Ejecuta la clase `Main` en `Main.java`.

```java
public static void main(String[] args) {
    LOGIN vista = new LOGIN();
    vista.setLocationRelativeTo(null);
    vista.setVisible(true);
    try {
        hc = new Conexion();
        System.out.println("Conectado");
        IU_GESTIONUSUARIO g = new IU_GESTIONUSUARIO();
        g.setLocationRelativeTo(null);
        g.setVisible(false);
    } catch (Exception e) {
        System.out.println("error al iniciar: " + e);
    }
}
```

## 🤝 Contribuciones
Las contribuciones son bienvenidas. Por favor, crea un fork del repositorio y envía un pull request con tus cambios.

## 📄 Licencia
Este proyecto está bajo la Licencia MIT. Consulta el archivo LICENSE para más detalles.

