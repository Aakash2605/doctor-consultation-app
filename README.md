# MedBook - Doctor Consultation Portal

An enterprise-grade, full-stack scheduling application. This repository contains both the Java Spring Boot backend engine and the Angular 21 (Standalone) frontend architecture.

##  Setup & Execution

### 1. Backend (Spring Boot)
1. Ensure Java 17+ and Maven are installed.
2. Open a terminal and navigate to the backend directory:
   `cd appointment`
3. Start the server:
   `mvn spring-boot:run`
4. The API will be available at `http://localhost:8080/api` (SQLite database auto-initializes).

### 2. Frontend (Angular 21)
1. Ensure Node.js and Angular CLI v21 are installed.
2. Open a **new, separate terminal** and navigate to the frontend directory:
   `cd medbookfrontend`
3. Install the required dependencies:
   `npm install`
4. Start the development server: 
   `ng serve`
5. Access the application at `http://localhost:4200`

---

##  Architectural Design Decisions

* **Standalone Components:** Built entirely without `NgModules`. Leveraged the modern Angular 21 control flow (`@if`, `@for`) and reactive `Signals` for highly optimized change detection and UI rendering.
* **Modern Injection:** Replaced traditional constructor injection with the functional `inject()` pattern for cleaner, more readable services and components.
* **Material UI Dark Theme:** Implemented a high-contrast, accessibility-friendly dark mode using Angular Material 3 to give the application a premium, focused feel.
* **Reactive Dictionary Mapping:** Used Angular Signals combined with `Map()` to dynamically cross-reference nested Database UUIDs with human-readable doctor names in real-time, preventing the "infinite loading" trap.

---

##  Handling Booking Conflicts (Advanced Challenge)

To solve the double-booking race condition, this application implements a hybrid concurrency model:

1. **Optimistic UI Updates (Frontend):** When a user clicks "Book", the UI *instantly* updates the slot to a grey, disabled state. This prevents the user from accidentally double-clicking the button while the network request is in flight.
2. **Pessimistic Locking (Backend):** The Spring Boot database engine uses strict transaction boundaries (`@Transactional`). If two distinct users attempt to book the exact same slot ID at the exact millisecond, the database throws a constraint violation for the second user. 
3. **Reconciliation:** If the backend rejects the transaction, the frontend catches the `400 Bad Request`, alerts the user via a Snackbar, and seamlessly reverts the UI back to the true database state.

---

##  Assumptions Made
* **Authentication:** For the scope of this assignment, Role-Based Access Control (RBAC) is simulated. The `/admin` route is accessible without JWT tokens to allow evaluators to easily test the doctor onboarding and slot generation features.
* **Session Management:** The user identity is handled via a simple string input (`userName`) during the booking phase, assuming a full user-auth table would be implemented in a production environment.
* **Database State:** It is assumed the Spring Boot backend will filter out booked slots from the `GET /slots` array. The frontend gracefully handles this by falling back to "Confirmed Appointment" if the backend hides the doctor metadata post-booking.