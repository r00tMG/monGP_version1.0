# Démarrage de l'application MonGP avec des images Docker existantes

Ce projet utilise une architecture multi-conteneurs pour exécuter :
- Une base de données PostgreSQL
- pgAdmin pour la gestion de la base
- Un backend Java Spring Boot

Les conteneurs communiquent via un réseau Docker personnalisé. PostgreSQL utilise un volume Docker pour la persistance des données.

##  Prérequis

- [Docker](https://docs.docker.com/get-docker/) installé
- Une connexion internet pour récupérer les images si elles ne sont pas en local

## 📦 Images utilisées

| Service         | Image                                 |
|----------------|----------------------------------------|
| PostgreSQL      | `postgres:latest`                     |
| pgAdmin         | `dpage/pgadmin4`                      |
| Backend Spring  | `r00tmg/backend-service-mongp:latest` |

---

##  Étapes de démarrage

### 1. Créer le réseau Docker

```bash
docker network create mongp-net
```

### 2. Lancer le conteneur PostgreSQL

```bash
docker run -d \
  --name db-postgres \
  --network mongp-net \
  -e POSTGRES_DB=db_mongp \
  -e POSTGRES_PASSWORD=1234 \
  -e POSTGRES_USER=admin \
  -v mongp_data:/var/lib/postgresql/data \
  -p 5432:5432 \
  postgres:latest
```

### 3. Lancer pgAdmin (facultatif)

```bash
docker run -d \
  --name page_admin \
  --network mongp-net \
  -e PGADMIN_DEFAULT_EMAIL=admin@gmail.com \
  -e PGADMIN_DEFAULT_PASSWORD=1234 \
  -p 5050:80 \
  dpage/pgadmin4
```

- Accès pgAdmin : [http://localhost:5050](http://localhost:5050)
- Identifiants : `admin@gmail.com` / `1234`

### 4. Lancer le backend Spring Boot

```bash
docker run -d \
  --name service-mongp \
  --network mongp-net \
  -e DB_URL=jdbc:postgresql://db-postgres:5432/db_mongp \
  -p 8001:8001 \
  r00tmg/backend-service-mongp:latest
```

---

##  Vérifier que tout fonctionne

```bash
docker ps
```

Tu dois voir les conteneurs suivants actifs :
- `db-postgres`
- `page_admin` (facultatif)
- `service-mongp`

---

##  Volumes utilisés

Un volume nommé `mongp_data` est automatiquement créé pour persister les données PostgreSQL.

```bash
docker volume ls
```

---

##  Arrêt & nettoyage

```bash
docker stop db-postgres page_admin service-mongp
docker rm db-postgres page_admin service-mongp
docker volume rm mongp_data
docker network rm mongp-net
```

---

##  Notes

- L’application backend lit la variable `DB_URL` dans `application.properties`.
- Le nom d'hôte `db-postgres` fonctionne grâce au réseau Docker personnalisé `mongp-net`.
