# NotasApp - Evaluación Parcial II

Aplicación Android para la gestión de notas personales. El proyecto implementa una arquitectura cliente-servidor completa, consumiendo una API REST propia para la autenticación de usuarios y el almacenamiento de las notas.

## 🎥 Video de Demostración
Puedes ver el funcionamiento completo de la aplicación en el siguiente enlace:
👉 *https://youtu.be/G7gRNipenq8* 👈

*(Nota: Reemplaza el texto entre paréntesis con el enlace a tu video).*

---

## Tecnologías Utilizadas

**Cliente (Android):**
*   **Lenguaje:** Kotlin
*   **Interfaz:** XML Layouts (ConstraintLayout y LinearLayout) + Material Design
*   **Arquitectura:** MVVM (Model-View-ViewModel)
*   **Red:** Retrofit 2 y OkHttp
*   **Asincronía:** Coroutines y LiveData
*   **Otros:** ViewBinding, RecyclerView con ListAdapter y DiffUtil.

**Servidor (Backend):**
*   **Entorno:** Node.js con Express.
*   **Base de Datos:** SQLite (persistencia en archivo local).
*   **Seguridad:** Autenticación mediante JSON Web Tokens (JWT).

---

## Estructura del Repositorio

*   **`/app` y archivos raíz:** Código fuente del proyecto de Android Studio (NotasApp).
*   **`/backend`:** Código del servidor en Node.js (`server.js` y dependencias).
*   *Nota:* El archivo `local.properties` y la carpeta `node_modules` han sido excluidos por seguridad y buenas prácticas.

---

## Instrucciones para Ejecutar el Proyecto

Sigue estos pasos para probar la aplicación localmente.

### 1. Iniciar el Backend
Abre una terminal en la carpeta `/backend` y ejecuta:
```bash
# Instalar las dependencias la primera vez
npm install

# Iniciar el servidor
node server.js
