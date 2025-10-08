# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a **Smart Greenhouse Environmental Monitoring System** (智能大棚环境监测系统) built with Spring Boot 2.7.15 and Java 17. It's a multi-module Maven project for managing agricultural equipment and environmental monitoring.

## Build and Development Commands

### Build Commands
```bash
# Clean build (skip tests)
mvn clean package -Dmaven.test.skip=true

# Full build with tests
mvn clean package

# Windows build script
.\bin\package.bat
```

### Run Commands
```bash
# Run with shell script (Linux/Mac)
./bin/ry.sh start    # Start application
./bin/ry.sh stop     # Stop application
./bin/ry.sh restart  # Restart application
./bin/ry.sh status   # Check status

# Run JAR directly
java -jar neu-admin/target/neu-admin.jar
```

### Test Commands
```bash
# Run all tests
mvn test

# Run specific module tests
mvn test -pl neu-system
mvn test -pl neu-framework
```

## Architecture Overview

### Module Structure
The project follows a modular architecture with clear separation of concerns:

- **neu-admin**: Main application module containing controllers and web layer (port 5502)
- **neu-framework**: Core framework with utilities, security, and common configurations
- **neu-system**: Business logic for system management (users, roles, menus, etc.)
- **neu-common**: Shared utilities and constants
- **neu-quartz**: Job scheduling module
- **neu-generator**: Code generation module
- **neu-lx**: Additional business module

### Key Technical Stack
- **Spring Boot 2.7.15**: Main framework
- **MyBatis 2.3.1**: ORM framework
- **Druid 1.2.18**: Database connection pool
- **MySQL 8.0.32**: Database
- **JWT 0.9.1**: Authentication
- **FastJSON 1.2.75**: JSON processing
- **Knife4j 4.4.0**: API documentation

### Configuration Structure
- Main config: `neu-admin/src/main/resources/application.yml`
- Database config: `neu-admin/src/main/resources/application-druid.yml`
- Application runs on port 5502 with context path "/"

### Package Structure
- Base package: `com.neu`
- Controllers: `com.neu.web.controller.*`
- Framework: `com.neu.framework.*`
- System: `com.neu.system.*`

### Database Notes
- Uses MySQL with Druid connection pool
- Database configuration excludes DataSourceAutoConfiguration (manually configured)
- File upload path configured as `D:\ruixiangzhcs\uploadPath` (Windows)

## Development Guidelines

When working with this codebase:
1. The project uses Spring Boot with manual datasource configuration
2. All modules are interconnected - changes in one may affect others
3. The system includes demo mode functionality (`demoEnabled: true`)
4. File paths are Windows-specific in current configuration
5. The application includes built-in code generation capabilities