# Hundir la Flota - Proyecto ABP 1º DAM

Este proyecto es una implementación del clásico juego **Hundir la Flota**, desarrollado como parte de un **Proyecto Basado en Problemas (ABP)** para el curso de **1º Desarrollo de Aplicaciones Multiplataforma (DAM)**. El juego está desarrollado utilizando **JavaFX** para la interfaz gráfica de usuario y se integra con una base de datos PostgreSQL para almacenar el progreso y los resultados de las partidas.

## Descripción

El juego **Hundir la Flota** permite a dos jugadores competir entre sí, intentando hundir la flota enemiga. Cada jugador tiene una flota de barcos que deben ser colocados en una cuadrícula. Los jugadores se alternan para disparar a las coordenadas de la cuadrícula del oponente hasta que hunden todos los barcos.

### Características
- **Interfaz gráfica**: Utiliza **JavaFX** para crear una interfaz de usuario interactiva.
- **Base de datos**: La información del juego (como el historial de partidas) se guarda en una base de datos PostgreSQL.
- **Modo 1vs1**: Permite jugar contra otro jugador en la misma máquina.

## Requisitos

### Software necesario:
1. **Java JDK 8 o superior**
2. **JavaFX**: Para compilar y ejecutar la interfaz gráfica.
3. **PostgreSQL**: Base de datos para almacenar la información del juego.

### Dependencias:
- **JDBC**: Para la conexión con la base de datos PostgreSQL.
- **PostgreSQL JDBC Driver**: Asegúrate de tener el archivo `postgresql-42.x.x.jar` en el directorio `lib` para poder conectar con la base de datos.

## Instalación

### 1. Clonar el repositorio

Primero, clona este repositorio en tu máquina local:

```bash
git clone https://github.com/usuario/repositorio.git
cd repositorio
