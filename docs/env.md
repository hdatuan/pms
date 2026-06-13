# Environment Variables Reference

The system uses configuration parameters defined in a `.env` file (copied from `.env.example`). These variables configure both the MySQL database container and the Java application environment.

---

## 1. Database Initialization & Credentials

These variables configure the MySQL service inside Docker:

- **`MYSQL_ROOT_PASSWORD`** (Default: `admin`)
  The administrative password for the MySQL `root` account.
- **`MYSQL_DATABASE`** (Default: `pms`)
  The name of the database created during initialization. The seeding script (`pms.sql`) will populate this database.
- **`MYSQL_PORT`** (Default: `3306`)
  The database port mapped from the MySQL container to the host machine.

---

## 2. Application Database Connection

These variables are injected into the Java web application container (`pms-app`) to connect to the MySQL database:

- **`DB_USER`** (Default: `root`)
  The username the Java application uses to connect to the database.
- **`DB_PASS`** (Default: `admin`)
  The password matching the `DB_USER`.

---

## 3. Database URL (Optional Override)

- **`DB_URL`**
  Stores the JDBC connection string. This is automatically formatted inside `docker-compose.yml` based on the `MYSQL_DATABASE` and container hostname (`db`):
  
  `jdbc:mysql://db:3306/${MYSQL_DATABASE}?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC&useUnicode=true&characterEncoding=UTF-8`

  *Note: You do not need to define `DB_URL` in your `.env` file unless you wish to manually override the target hostname or connection arguments (e.g. running outside of Docker).*
