# Xpace - Sistema de Gestión de Expediciones Espaciales

## Descripción
Xpace es un sistema desarrollado para gestionar expediciones espaciales de forma integral.  
Permite modelar naves, planetas, bases de lanzamiento, tripulantes y el ciclo completo de las misiones.

---

## Funcionalidades

### Gestión de Naves
El sistema contempla distintos tipos de naves:

- **Sondas**: naves autónomas sin tripulación  
- **Transbordadores**: transporte de tripulación con capacidad máxima  
- **Cargueros**: transporte de recursos medidos en toneladas  

El consumo de combustible se calcula en función de:
- Consumo base de la nave  
- Carga o tripulación transportada  
- Distancia al destino  

---

### Ciclo de Vida de Misiones
Las misiones pueden encontrarse en los siguientes estados:

- BORRADOR  
- EN CURSO  
- COMPLETADA  
- FALLIDA  
- CANCELADA  

Para el lanzamiento se validan:
- Estado de la misión  
- Autonomía de la nave  
- Aptitud de la tripulación  
- Ubicación en una base de lanzamiento  

El sistema también permite identificar misiones de alto riesgo según las condiciones del planeta y la duración estimada.

---

### Gestión de Tripulantes

Se modelan distintos roles:

- Comandante  
- Piloto  
- Ingeniero  
- Científico  
- Médico  

Cada tripulante posee un perfil de aptitud:
- Conformista  
- Prudente  
- Explorador  
- Veterano  
- Cauteloso  
- Exigente  

El salario se calcula como:
- Salario base  
- Bonus según rol e historial de misiones  

---

### Exploración de Planetas

La habitabilidad de un planeta se evalúa considerando:
- Temperatura  
- Gravedad  
- Presencia de agua líquida  
- Toxicidad  
- Radiación  

La peligrosidad se calcula en base a:
- Radiación  
- Toxicidad atmosférica  
- Actividad tectónica  

---

## Tecnologías Utilizadas

- Lenguaje: Kotlin  
- Gestor de dependencias: Gradle (Kotlin DSL)  
- Librería externa: org.uqbar-project:geodds-xtend (cálculo de distancias)  
- Integración continua: GitHub Actions  

---

## Ejecución del Proyecto

```bash
./gradlew build
./gradlew test
