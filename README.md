🍬 Sweet Shop Management System
A professional, full-stack inventory and sales management system designed for sweet shops. This application features a secure admin dashboard to manage stock, track real-time inventory, and process simulated sales with a modern, responsive UI.
+2

🚀 Live Links
Frontend (Vercel): [https://your-app-name.vercel.app](https://sweet-shop-full-stack.vercel.app/)

Backend API (Render): [https://sweet-shop-backend.onrender.com](https://sweet-shop-fullstack-jthb.onrender.com/)

Database (Supabase): [Managed PostgreSQL instance.](https://xawksevwhwrdvnmhydmo.supabase.co)

🛠️ Tech Stack
Backend
- Language: Java 21.

- Framework: Spring Boot 3.3.0.

- Security: Spring Security + JWT (JSON Web Tokens) with 256-bit encryption.

- Database: PostgreSQL (Hosted via Supabase).

- Build Tool: Maven.

Frontend
- Languages: Vanilla JavaScript (ES6+), HTML5, CSS3.

- Design: Custom Modern UI with smooth CSS transitions and mesh gradients.

- Architecture: Decoupled Frontend-Backend communication via Fetch API.

✨ Features
- Secure Authentication: User registration and login protected by JWT stored in LocalStorage.

- Smart Inventory Dashboard: Visual display of sweets with a custom-built color-hashing algorithm for card headers.

-Stock Management:
  ->Add: Integrated admin interface to input sweet names, prices, and quantities.

  ->Purchase: Real-time stock reduction upon purchase with "Sold Out" state handling.

  ->Delete: Full CRUD functionality to remove inventory items.

  ->Search & Organize:

  ->Live Search: Filter sweets instantly by name.

  ->Dynamic Sorting: Organize by price (High to Low / Low to High) or newest additions.

  ->Responsive UX: Mobile-first design using CSS Flexbox and Grid, featuring entrance animations and floating effects.

⚙️ Local Setup Instructions
Follow these steps to set up the development environment.

1. Prerequisites
- Java JDK 17 or 21.

- Browser with Developer Tools enabled.

- Supabase account (or local PostgreSQL).

2. Backend Configuration (final-backend)
a. Navigate to the directory: cd final-backend.

b. Open src/main/resources/application.properties.

c. Configure your environment variables or local properties

d. Run the application using your IDE or Maven: ./mvnw spring-boot:run.

3. Frontend Configuration (final-frontend)
a. Open script.js.

b. Update the API_BASE variable to point to your local server:

c. Launch index.html using a local server (e.g., VS Code Live Server).

🔒 Security Best Practices
- Environment Variables: Sensitive data like DB passwords and JWT keys are managed through system environment variables to prevent exposure in version control.

- CORS Policy: Strict Cross-Origin Resource Sharing (CORS) configuration to allow only authorized frontend domains.

- Password Hashing: BCrypt hashing utilized for all stored user credentials.

Developed by Chanchal | 2026 Sweet Shop Systems.
Password Hashing: BCrypt hashing utilized for all stored user credentials.

Developed by Chirag Yadav | 2026 Sweet Shop Systems.
