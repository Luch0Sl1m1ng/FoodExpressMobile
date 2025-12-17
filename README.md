# FoodExpressMobile

Aplicación móvil desarrollada en **Kotlin** con **Jetpack Compose** para gestionar pedidos de comida rápida, como parte de la Evaluación Parcial 4 de la asignatura DSY1105.

## 1. Nombre del proyecto

**FoodExpressMobile**

## 2. Integrantes

- [Tu nombre completo] – Sección [tu sección]

## 3. Arquitectura y patrón usado

La aplicación está construida con el patrón **MVVM**:

- `model/` → modelos de datos: `Product`, `OrderTotals`.
- `data/` → lógica de negocio y acceso a datos: `OrderCalculator`, `FoodRepository`.
- `ui/state/` → `FoodUiState`, que representa el estado de la pantalla (productos, carrito, totales, errores, vip, etc.).
- `ui/viewmodel/` → `FoodViewModel`, que coordina la lógica de la app: carga productos, maneja el carrito, calcula totales, llama a los repositorios.
- `ui/screens/` → pantallas en Jetpack Compose:
  - `ProductListScreen`
  - `OrderSummaryScreen`
- `network/` → integración con microservicios y API externa usando Retrofit:
  - `FoodApiService`
  - `AdviceApiService`
  - `ApiClients`

## 4. Principales funcionalidades

- Listado de productos de comida tipo “menú”.
- Selección de productos para agregarlos al carrito.
- Cálculo de subtotal, descuento VIP (10%), IVA (19%) y total del pedido.
- Resumen del pedido con detalle de totales.
- Muestra un **“Consejo del día”** obtenido desde una API externa.
- Manejo de estados de carga y error (mensaje “Error al cargar productos…” y botón **Reintentar** si el microservicio no está disponible).

## 5. Endpoints utilizados

### Microservicio (Spring Boot)

> Los microservicios fueron desarrollados en las experiencias 2 y 3.  
> La app móvil está preparada para consumirlos mediante Retrofit.

- **Base URL desde el emulador Android:**  
  `http://10.0.2.2:8080/api/`

- `GET /products`  
  Devuelve la lista de productos que se muestra en `ProductListScreen`.

- `POST /orders`  
  Recibe el pedido (items seleccionados + flag VIP) y devuelve un `OrderResponseDto` con un mensaje de confirmación.

Implementación en la app:

- `network/FoodApiService.kt`
- `network/ApiClients.kt`
- `data/FoodRepository.kt`
- `ui/viewmodel/FoodViewModel.kt`

### API externa

- **Base URL:** `https://api.adviceslip.com/`
- **Endpoint:** `GET /advice`  
  Devuelve un texto de consejo que se muestra en el resumen del pedido.

Implementación:

- `network/AdviceApiService.kt`
- `network/ApiClients.kt` (`adviceApi`)
- `data/FoodRepository.kt` (`loadExternalAdvice`)
- `ui/viewmodel/FoodViewModel.kt` (`externalAdvice`)

## 6. Pasos para ejecutar

### 6.1 Ejecutar solo la app móvil

1. Clonar este repositorio.
2. Abrir el proyecto en **Android Studio**.
3. Seleccionar la configuración **app**.
4. Ejecutar en un emulador o dispositivo físico (Run ▶).
5. Si el microservicio NO está encendido, la app mostrará un mensaje:
   - “Error al cargar productos: failed to connect to 10.0.2.2…”
   - Se puede usar el botón **Reintentar** para probar nuevamente la conexión.

### 6.2 Ejecutar app + microservicio (cuando se tenga el backend)

1. Levantar el proyecto Spring Boot (Exp 2/3) en `http://localhost:8080` con los endpoints:
   - `GET /api/products`
   - `POST /api/orders`
2. Verificar esos endpoints con Postman o navegador.
3. Ejecutar la app en el emulador:
   - Desde el emulador se usa `http://10.0.2.2:8080/api/...`
4. La app carga los productos desde el microservicio y puede enviar el pedido.

### 6.3 Ejecutar pruebas unitarias

1. En Android Studio, abrir `OrderCalculatorTest.kt` en:
   `app/src/test/java/com/example/foodexpressmobile/data/`
2. Click derecho → **Run 'OrderCalculatorTest'**.
3. Verificar que los tests pasen en verde (cálculo de totales con y sin VIP, carrito vacío, etc.).

## 7. APK firmado y .jks

Se generó un APK firmado para la app:

- APK firmado: `app/release/app-release.apk`
- Nombre sugerido para distribución: `FoodExpressMobile-release.apk`
- Keystore utilizada: `app/keystore/foodexpress.jks`

El APK fue generado con:

- **Build → Generate Signed Bundle / APK… → APK → release**

Este APK es el que se adjunta en la entrega de la evaluación (AVA).
