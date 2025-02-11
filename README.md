# ProjetoDAE

## Monitor System

A Java-based project designed to manage and monitor smart packages and related components using Jakarta EE. This project utilizes RESTful APIs for seamless interaction between components and emphasizes efficient entity management.

## Features

- **Entity Management**: CRUD operations for packages, products, sensors, volumes,...
- **REST API**: Endpoints to interact with entities such as Packages, Products, and Sensors.

## Project Structure

```plaintext
src/
├── main/
│   ├── java/
│   │   ├── pt.ipleiria.estg.dei.ei.dae.monitor/
│   │   │   ├── ejbs/               # Business logic (EJBs)
│   │   │   ├── entities/           # Entities for database mapping
│   │   │   ├── providers/          # Utility providers
│   │   │   ├── ws/                 # RESTful web services
│   ├── resources/
│   │   ├── META-INF/               # Persistence configuration
├── test/
│   ├── java/                       # Test cases for the application
```

## Technologies Used

- **Java 17**
- **Jakarta EE**
- **JPA (Hibernate)**
- **RESTful APIs**
- **PostgreSQL**

