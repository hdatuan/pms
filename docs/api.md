# API & Route Reference

Since this application utilizes **Server-Side Rendering (SSR)** with JSP, all routes either serve HTML (JSP forward) or process HTTP POST requests (form actions).

---

## 1. Authentication

| Method | Path | Allowed Roles | Parameters (Form Body) | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/login` | Public | None | Serves the login page. Redirects to `/home` if already authenticated. |
| **POST** | `/login` | Public | `email`, `password` | Processes authentication. On success, stores session and redirects to `/home`. |
| **GET** | `/logout` | Authenticated | None | Invalidates user session and redirects to `/login`. |

---

## 2. Dashboard & User Profile

| Method | Path | Allowed Roles | Parameters | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/home` | All Roles | None | Renders the primary dashboard page showing visual progress charts. |
| **GET** | `/profile` | All Roles | None | Renders the personal profile page listing tasks assigned to the logged-in user. |
| **GET** | `/profile-edit`| All Roles | Query: `jobId`, `taskId` | Renders a form to update task progress. Protected by ownership validation. |
| **POST**| `/profile-edit`| All Roles | Body: `jobId`, `taskId`, `start_date`, `end_date`, `status_id` | Updates task date and status. Protected by ownership validation. |

---

## 3. Role Management

| Method | Path | Allowed Roles | Parameters | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/role` | Admin | None | Displays the list of all system roles. |
| **GET** | `/role-add` | Admin | None | Renders the role creation form. |
| **POST** | `/role-add` | Admin | Body: `name`, `description` | Saves a new role. Rejects duplicate role names. |
| **GET** | `/role-edit` | Admin | Query: `id` | Renders the role edit form pre-filled with data. |
| **POST** | `/role-edit` | Admin | Body: `id`, `name`, `description` | Commits changes to the selected role. |
| **GET** | `/role-delete`| Admin | Query: `id` | Deletes a role. Blocks operation if users are linked to the role. |

---

## 4. User Management

| Method | Path | Allowed Roles | Parameters | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/user` | All Roles | None | Displays the user table containing all registered staff members. |
| **GET** | `/user-add` | Admin | None | Renders the user creation form. |
| **POST** | `/user-add` | Admin | Body: `email`, `password`, `fullname`, `role-id` | Creates a new user. Rejects duplicate emails. |
| **GET** | `/user-edit` | Admin | Query: `id` | Renders the user edit form. |
| **POST** | `/user-edit` | Admin | Body: `id`, `email`, `fullname`, `role-id`, `password` (optional) | Updates user profile. Password is unchanged if left blank. |
| **GET** | `/user-delete`| Admin | Query: `id` | Deletes a user profile from the database. |

---

## 5. Job (Project) Management

| Method | Path | Allowed Roles | Parameters | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/groupwork` | All Roles | None | Displays all projects. Action buttons are hidden for Staff members. |
| **GET** | `/groupwork-add` | Admin / Manager| None | Renders the project creation form. |
| **POST** | `/groupwork-add` | Admin / Manager| Body: `name`, `start_date`, `end_date` | Creates a new project. |
| **GET** | `/groupwork-edit` | Admin / Manager| Query: `id` | Renders the project edit form. |
| **POST** | `/groupwork-edit` | Admin / Manager| Body: `id`, `name`, `start_date`, `end_date` | Updates project metadata. |
| **GET** | `/groupwork-delete`| Admin / Manager| Query: `id` | Deletes a project and all associated tasks cascade. |
| **GET** | `/groupwork-details`| Admin / Manager| Query: `id` | Renders detailed charts and member task lists for a project. |

---

## 6. Task Management

| Method | Path | Allowed Roles | Parameters | Description |
| :--- | :--- | :--- | :--- | :--- |
| **GET** | `/task` | All Roles | None | Displays all tasks in the system. Actions hidden for Staff. |
| **GET** | `/task-add` | Admin / Manager| None | Renders task creation form with dynamic project/member lists. |
| **POST** | `/task-add` | Admin / Manager| Body: `name`, `job_id`, `user_id`, `start_date`, `end_date`, `status_id` | Spawns a new task. |
| **GET** | `/task-edit` | Admin / Manager| Query: `id` | Renders the task edit form. |
| **POST** | `/task-edit` | Admin / Manager| Body: `id`, `name`, `job_id`, `user_id`, `start_date`, `end_date`, `status_id` | Commits changes to a task. |
| **GET** | `/task-delete`| Admin / Manager| Query: `id` | Deletes the task from the system database. |
