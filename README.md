# Clinic Microservices — Monitoring with Prometheus & Grafana

## Architecture Overview

```
                    ┌─────────────────┐
                    │  Grafana :3000   │  ← Visualizes metrics
                    └────────┬────────┘
                             │
                    ┌────────▼────────┐
                    │ Prometheus :9090 │  ← Collects metrics
                    └────────┬────────┘
                             │ scrapes /actuator/prometheus
          ┌──────────────────┼───────────────────┐
          │                  │                   │
  ┌───────▼───────┐  ┌───────▼───────┐  ┌───────▼───────┐
  │ Appointment   │  │    Doctor     │  │    Patient    │
  │ Service :9002 │  │ Service :9003 │  │ Service :9004 │
  └───────┬───────┘  └───────┬───────┘  └───────┬───────┘
          │                  │                   │
          └──────────────────▼───────────────────┘
                      ┌──────────────┐
                      │  MySQL :3306 │
                      │  3 databases │
                      └──────────────┘

  ┌────────────────────────┐   ┌────────────────────┐
  │  API Gateway :9001     │   │ Eureka Server :8761 │
  └────────────────────────┘   └────────────────────┘
```

## What Changed from Original Code

### MongoDB → MySQL
| File | Change |
|------|--------|
| `pom.xml` (all 3 services) | `spring-boot-starter-data-mongodb` → `spring-boot-starter-data-jpa` + `mysql-connector-j` |
| `model/Appointment.java` | `@Document` → `@Entity @Table`, `@Id String` → `@Id @GeneratedValue Long` |
| `model/Doctor.java` | Same as above |
| `model/Patient.java` | Same; `insuranceId: String` → `@ManyToOne Insurance insurance` |
| `model/Insurance.java` | Same `@Document` → `@Entity @Table` changes |
| `repository/*.java` | `MongoRepository<T, String>` → `JpaRepository<T, Long>` |
| `application.yml` | Added `spring.datasource` + `spring.jpa` MySQL config |

### Prometheus + Grafana Monitoring Added
| File | Purpose |
|------|---------|
| `pom.xml` (all 5 services) | Added `spring-boot-starter-actuator` + `micrometer-registry-prometheus` |
| `application.yml` (all 5) | Exposes `/actuator/prometheus`, `/actuator/health`, `/actuator/metrics` |
| `prometheus.yml` | Tells Prometheus where to scrape each service |
| `grafana/provisioning/` | Auto-configures Prometheus datasource + pre-built dashboard |
| `docker-compose.yml` | Wires everything together |

---

## Prerequisites

- Docker Desktop installed and running
- At least 4 GB RAM allocated to Docker

---

## How to Run the Full Stack

### Step 1 — Copy changed files into your project

Replace or merge these files in your existing project:
- Each service's `pom.xml`
- Each model and repository class
- Add `src/main/resources/application.yml` to each service
- Add `Dockerfile` to each service root
- Place `docker-compose.yml`, `prometheus.yml`, `mysql-init/`, and `grafana/` in project root

### Step 2 — Build and start everything

```bash
# From project root (where docker-compose.yml is)
docker-compose up --build
```

First build will take 5–10 minutes (Maven downloads dependencies inside containers).

### Step 3 — Verify services are up

| Service | URL |
|---------|-----|
| Eureka Dashboard | http://localhost:8761 |
| API Gateway | http://localhost:9001 |
| Appointment Swagger | http://localhost:9002/swagger-ui/index.html |
| Doctor Swagger | http://localhost:9003/swagger-ui/index.html |
| Patient Swagger | http://localhost:9004/swagger-ui/index.html |
| Prometheus | http://localhost:9090 |
| Grafana | http://localhost:3000 |

### Step 4 — Open Grafana

1. Go to http://localhost:3000
2. Login: **admin / admin123**
3. Navigate to Dashboards → Clinic → **Clinic Microservices - Monitoring Dashboard**

---

## Prometheus Targets Check

Go to http://localhost:9090/targets — you should see all 5 services listed as **UP**.

If a target shows **DOWN**, check:
```bash
docker-compose logs <service-name>
```

---

## Metrics Available (Key Ones)

| Metric | Description |
|--------|-------------|
| `up` | Is the service running (1=up, 0=down) |
| `http_server_requests_seconds_count` | HTTP request count by URI and status |
| `http_server_requests_seconds_sum` | Total time spent on HTTP requests |
| `jvm_memory_used_bytes` | JVM heap/non-heap memory |
| `jvm_threads_live_threads` | Live thread count |
| `process_cpu_usage` | CPU usage 0.0–1.0 |
| `process_uptime_seconds` | How long service has been up |
| `jvm_gc_pause_seconds_sum` | GC pause time |
| `hikaricp_connections_active` | Active DB connection pool connections |

---

## MySQL Databases

Each service gets its own isolated database inside the shared MySQL instance:

| Service | Database |
|---------|----------|
| Appointment Service | `clinic_appointment_db` |
| Doctor Service | `clinic_doctor_db` |
| Patient Service | `clinic_patient_db` |

Tables are auto-created by Hibernate (`ddl-auto: update`) on first startup.

Connect to MySQL directly:
```bash
docker exec -it clinic-mysql mysql -u clinic_user -pclinic_pass clinic_doctor_db
```

---

## ID Type Change (String → Long)

Original MongoDB used String IDs (UUIDs). MySQL uses auto-increment Long IDs.

If your controllers still use `String` path variables, update them to `Long`:
```java
// Before (MongoDB)
@GetMapping("/doctors/{id}")
public Doctor getDoctor(@PathVariable String id) { ... }

// After (MySQL/JPA)
@GetMapping("/doctors/{id}")
public Doctor getDoctor(@PathVariable Long id) { ... }
```

---

## Stopping the Stack

```bash
# Stop all containers
docker-compose down

# Stop and remove all data volumes (clean slate)
docker-compose down -v
```

---

## Useful Docker Commands

```bash
# View logs for a service
docker-compose logs -f appointment-service

# Restart one service without rebuilding
docker-compose restart doctor-service

# Rebuild only one service
docker-compose up --build appointment-service

# Check running containers
docker-compose ps
```
