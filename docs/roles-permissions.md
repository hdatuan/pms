# Roles & Permissions (RBAC)

The Project Management System enforces **Role-Based Access Control (RBAC)** to govern routing boundaries and user capabilities. This is achieved via a centralized security filter architecture.

---

## 1. Security Architecture (Java Servlet Filters)

We implement a two-tiered filter pipeline in the web server to secure application resources:

1. **Authentication (`AuthenticationFilter`):**
   - Intercepts all paths (except `/login` and static resources like CSS/JS).
   - Validates if a user session exists.
   - Redirects unauthenticated users to `/login`.
2. **Authorization (`AuthorizationFilter`):**
   - Executed immediately after authentication succeeds.
   - Compares the logged-in user's role against access lists mapping to specific URLs.
   - Forwards unauthorized attempts to `/403` (Forbidden Page).

*Note: The filter execution order is strictly guaranteed by the declaration order in `src/main/webapp/WEB-INF/web.xml`.*

---

## 2. Defined Roles

The application recognizes 3 distinct roles, modeled under the `UserRole` enum:

| Role ID | Role Code | Description |
| :---: | :--- | :--- |
| **1** | `ADMIN` | Full control. Can perform user/role management, project planning, and task delegations. |
| **2** | `MANAGER` | Project management. Can create, edit, view, and assign tasks within projects, and inspect project metrics. |
| **3** | `STAFF` | Execution role. Can view projects/tasks and update the progress of tasks assigned directly to them. |

---

## 3. Permission Matrix

### System Administration (Admin Only)

| Function | Endpoint / Route | Admin | Manager | Staff |
| :--- | :--- | :---: | :---: | :---: |
| Create User | `GET / POST /user-add` | ✅ | ❌ | ❌ |
| Edit User | `GET / POST /user-edit` | ✅ | ❌ | ❌ |
| Delete User | `GET /user-delete` | ✅ | ❌ | ❌ |
| Create Role | `GET / POST /role-add` | ✅ | ❌ | ❌ |
| Edit Role | `GET / POST /role-edit` | ✅ | ❌ | ❌ |
| Delete Role | `GET /role-delete` | ✅ | ❌ | ❌ |

### Project & Task Management

| Function | Endpoint / Route | Admin | Manager | Staff |
| :--- | :--- | :---: | :---: | :---: |
| Create Project | `GET / POST /groupwork-add` | ✅ | ✅ | ❌ |
| Edit Project | `GET / POST /groupwork-edit` | ✅ | ✅ | ❌ |
| Delete Project | `GET /groupwork-delete` | ✅ | ✅ | ❌ |
| Project Progress Details | `GET /groupwork-details` | ✅ | ✅ | ❌ |
| Create Task | `GET / POST /task-add` | ✅ | ✅ | ❌ |
| Edit Task | `GET / POST /task-edit` | ✅ | ✅ | ❌ |
| Delete Task | `GET /task-delete` | ✅ | ✅ | ❌ |

### Common Operations (All Authenticated Users)

| Function | Endpoint / Route | Admin | Manager | Staff |
| :--- | :--- | :---: | :---: | :---: |
| Dashboard | `GET /home` | ✅ | ✅ | ✅ |
| Personal Profile | `GET /profile` | ✅ | ✅ | ✅ |
| Update Personal Task Progress | `GET / POST /profile-edit` | ✅ | ✅ | ✅ |
| Read User List | `GET /user` | ✅ | ✅ | ✅ |
| Read Project List | `GET /groupwork` | ✅ | ✅ | ✅ |
| Read Task List | `GET /task` | ✅ | ✅ | ✅ |
| Logout | `GET /logout` | ✅ | ✅ | ✅ |

---

## 4. Developer Guide: Protecting New Routes

If you build a new controller and want to restrict its endpoints, you do not need to add authentication code inside the servlet. Instead, follow these steps:

1. Open `src/main/java/hdatuan/filter/AuthorizationFilter.java`.
2. Add your servlet path (e.g. `"/new-feature"`) into the appropriate collection:
   - `ADMIN_ONLY_PATHS` (for Admin only access)
   - `ADMIN_AND_MANAGER_PATHS` (for Admin and Manager access)
3. For views (JSP files), use JSTL conditional tags combined with session values to hide buttons:
   ```jsp
   <c:if test="${sessionScope.user.roleID == 1}">
       <a href="/role-add">Create Role</a>
   </c:if>
   ```
