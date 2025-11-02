# 🌊 AquaFlux - Sistema IoT de Detección de Fugas

<div align="center">

**Aplicación móvil multiplataforma para monitoreo en tiempo real de fugas de agua**

[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.20-blue.svg?style=flat&logo=kotlin)](https://kotlinlang.org)
[![KMP](https://img.shields.io/badge/Kotlin-Multiplatform-orange.svg)](https://kotlinlang.org/docs/multiplatform.html)
[![Android](https://img.shields.io/badge/Android-24+-green.svg?style=flat&logo=android)](https://www.android.com)

</div>

---

## 📋 Descripción

**AquaFlux** es una aplicación móvil desarrollada con **Kotlin Multiplatform** que permite monitorear en tiempo real el estado de sistemas de agua utilizando sensores IoT conectados a **Adafruit IO**. La aplicación detecta fugas automáticamente y envía notificaciones push a los usuarios.

### 🎯 Características Principales

- ✅ **Autenticación JWT** - Login y registro seguro de usuarios
- ✅ **Dashboard en Tiempo Real** - Visualización de estado de fugas y litros fugados
- ✅ **Notificaciones Push** - Alertas automáticas mediante Firebase Cloud Messaging
- ✅ **Auto-Refresh** - Actualización automática cada 30 segundos
- ✅ **Multiplataforma** - Comparte el 80% del código entre Android e iOS
- ✅ **Clean Architecture** - Código mantenible y escalable

## 🚀 Inicio Rápido

### Prerequisitos

- Android Studio Arctic Fox o superior
- JDK 11+
- Backend Next.js corriendo en vercel
- Cuenta de Firebase (para notificaciones)

### Instalación

1. **Abrir proyecto en Android Studio**
2. **Sincronizar Gradle**
3. **Ejecutar en emulador o dispositivo**

```bash
# Compilar
./gradlew :composeApp:assembleDebug

# Instalar
./gradlew :composeApp:installDebug
```

## 🏗️ Arquitectura

```
┌─────────────────────┐
│  Presentation       │  ← ViewModels, UI (Compose)
├─────────────────────┤
│  Domain             │  ← Use Cases
├─────────────────────┤
│  Data               │  ← Repositories, APIs
└─────────────────────┘
```

**Clean Architecture** con separación clara de responsabilidades.

## 🛠️ Stack Tecnológico

- **Kotlin 2.2.20** - Lenguaje
- **Ktor Client 2.3.12** - HTTP
- **Jetpack Compose** - UI
- **Firebase Cloud Messaging** - Notificaciones
- **DataStore** - Almacenamiento
- **WorkManager** - Background tasks

## 📡 API Endpoints

```
POST /api/auth/login
POST /api/auth/register
GET  /api/feeds/dashboard
GET  /api/feeds/leak-status
GET  /api/feeds/water-flow
GET  /api/feeds/water-flow-chart
```

## 🎨 Colores del Proyecto

```
Negro:        #000814
Azul Oscuro:  #001D3D
Azul Claro:   #003566
Amarillo Osc: #FFC300
Amarillo Clar:#FFD60A
```

