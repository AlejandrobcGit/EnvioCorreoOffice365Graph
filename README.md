# 📧 Envío de Correos con Microsoft Graph (Java)

## 📖 Descripción

Aplicación Java que permite el envío de correos electrónicos utilizando la API de Microsoft Graph y Exchange Online.  
El proyecto implementa autenticación basada en OAuth 2.0 y realiza peticiones REST mediante el SDK oficial de Microsoft Graph para Java.

Se trata de un cliente ligero orientado a demostrar la integración con servicios cloud de Microsoft desde una aplicación Java.

---

## 🧱 Arquitectura

- Aplicación Java standalone (CLI)
- Uso de SDK oficial de Microsoft Graph
- Integración mediante HTTP/REST
- Configuración externa mediante archivo `.env`

---

## 🛠️ Tecnologías utilizadas

### Lenguaje
- Java 17

### Build y gestión de dependencias
- Maven

### Librerías principales

- **Microsoft Graph SDK for Java**
  - Manipulación de mensajes y llamadas a la API Graph
- **Azure Identity**
  - Autenticación con `ClientSecretCredential`
- **Dotenv Java**
  - Gestión de variables de entorno desde archivo `.env`
- **SLF4J (simple logger)**
  - Logging básico
- **JUnit**
  - Testing (incluido por defecto)

---

## 🔐 Autenticación

El proyecto utiliza un modelo de autenticación basado en:

- OAuth 2.0
- Client Credentials Flow
- Generación de token de acceso para consumir Microsoft Graph

---

## 📡 Funcionalidad principal

- Construcción de mensajes de correo:
  - asunto
  - cuerpo
  - destinatarios
- Envío de emails mediante Microsoft Graph API
- Uso del endpoint: POST https://graph.microsoft.com/v1.0/users/{user-id}/sendMail

---

## 🔄 Flujo de ejecución

1. Carga de variables desde `.env`
2. Autenticación mediante Azure Identity
3. Inicialización del cliente Graph
4. Construcción del email
5. Envío mediante API Graph

---

## ⚙️ Configuración

El proyecto utiliza variables de entorno cargadas desde `.env`.

Ejemplo de variables necesarias:

```env
tenantId=
clientId=
clientSecret=
senderUpn=
to=
