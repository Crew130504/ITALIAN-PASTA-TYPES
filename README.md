
# Aplicativo Gestión de Tipos de Pasta

## Descripción

Este proyecto consiste en el desarrollo de un aplicativo de escritorio para gestionar información sobre diferentes tipos de pasta italiana. El aplicativo permite:

- Cargar datos iniciales de tipos de pasta desde un archivo de propiedades
- Almacenar los tipos de pasta cargados en una base de datos
- Seleccionar y visualizar las características de un tipo de pasta
- Realizar operaciones CRUD sobre los datos:
  - Insertar nuevos tipos de pasta
  - Consultar tipos de pasta
  - Eliminar tipos de pasta
  - Modificar tipos de pasta
- Exportar los datos almacenados a un archivo cuando se sale de la aplicación

## Tecnologías utilizadas

- Java 
- JDBC
- Patrón MVC
- Patrón DAO
- Principios SOLID
- Swing para la interfaz gráfica

## Estructura del proyecto

```
├── src
|   ├── model
|   |   └── clases del modelo  
|   ├── view 
|   |   └── clases de la vista (Swing)
|   └── controller
|       └── clases del controlador
├── resources
|   └── archivo de propiedades iniciales
├── docs
|   ├── diagrama de clases
|   └── nombres de integrantes
```

## Instalación

1. Clona este repositorio
2. Importa el proyecto en tu IDE preferido (Eclipse, IntelliJ, etc)
3. Configura la conexión a la base de datos (archivo SQL incluido en /resources)  
4. Ejecuta la clase principal para iniciar la aplicación

## Autores

Brayan Steven Sanchez- 20212020092

Valentina Parra-20212020025

Juan Pablo Angulo Guerrero-20222020099

## Licencia

Este proyecto se encuentra bajo la Licencia GNU General Public License v3.0.
