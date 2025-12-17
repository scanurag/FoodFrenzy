# FoodFrenzy
FoodFrenzy is a comprehensive system designed for managing customers, inventory, and orders. It offers secure authentication, role-based access control, and database integration using MySQL. Built with Spring Boot and Thymeleaf, the application provides a seamless experience for admin and staff members.

![Screenshot 2024-10-02 123332](https://github.com/user-attachments/assets/1382d32f-3cbb-40c3-b6b5-9fc55cd5176f)


## Features

- **Customer Management**: Easily add, update, and delete customer information.
- **Inventory Management**: Keep track of your inventory items, including stock levels and pricing.
- **Order Management**: Manage customer orders, including order creation, updates, and status tracking.
- **User Authentication**: Secure login and authentication for admin and staff members.
- **Role-Based Access Control**: Define roles and permissions for different user types.
- **Thymeleaf Templates**: Utilizes Thymeleaf for dynamic HTML templates.
- **Database Integration**: Integrated with MySQL for data storage and retrieval.

## Technology Stack

- **Backend**: Spring Boot, Java 8, Spring MVC, Spring Data JPA (Hibernate)
- **Frontend**: Thymeleaf, HTML, CSS, JavaScript
- **Database**: MySQL
- **IDE**: Eclipse, Spring Tool Suite (STS)

## Prerequisites

Before running this project, ensure you have the following installed:

- Java 8
- MySQL
- Maven
- Eclipse or Spring Tool Suite (STS)

## Setup and Installation
# 🎁 Donate

<a href="https://buymeacoffee.com/1122anuragg">
  <img src="https://user-images.githubusercontent.com/25067102/154570688-9e143f2b-fee3-4b05-a9d2-a7a3013b2b51.png" />
<a/>
1. Clone the repository:
    ```bash
    git clone https://github.com/your-repository-url/FoodFrenzy.git
    ```

2. Navigate to the project directory:
    ```bash
    cd FoodFrenzy
    ```

3. Configure MySQL Database:
    - Create a new MySQL database.
    - Update `application.properties` with your MySQL credentials:
      ```properties
      spring.datasource.url=jdbc:mysql://localhost:3306/foodfrenzy
      spring.datasource.username=root
      spring.datasource.password=root
      spring.jpa.hibernate.ddl-auto=update
      ```

4. Run the project:
    ```bash
    mvn spring-boot:run
    ```

5. Access the application:
    - Navigate to `http://localhost:8080` in your browser. 
## Website Screenshot

Here is a preview of the FoodFrenzy interface:

![Screenshot 2025-05-19 143351](https://github.com/user-attachments/assets/3e34f54c-c986-42ac-96a4-ed7ad18035a6)
![Screenshot 2025-05-19 143427](https://github.com/user-attachments/assets/a4046d4e-8c3d-4629-8913-5543d709e80e)
![Screenshot 2025-05-19 143441](https://github.com/user-attachments/assets/09c92348-ec06-4607-9ae4-88b28cc1e0ec)
![Screenshot 2025-05-19 143452](https://github.com/user-attachments/assets/0e73aece-a28c-413d-9363-245358a0e439)
![Screenshot 2025-05-19 143504](https://github.com/user-attachments/assets/ac26bd92-0fcd-473d-a175-9bc45fdb12ba)


![image](https://github.com/user-attachments/assets/3d407958-efbe-449f-b4e1-5b08b9f8a28b)


![Screenshot 2024-08-24 194958](https://github.com/user-attachments/assets/c11a4710-69f8-42fd-b9d7-2b5278b2c8a3) 


![Screenshot 2024-08-24 220831](https://github.com/user-attachments/assets/d3cd3cdd-cda5-460a-a253-24e45cf600b0)  
![Screenshot 2024-08-24 195054](https://github.com/user-attachments/assets/733afb94-a251-4a6d-86a4-3a124e8c3469)
![Screenshot 2024-08-24 195106](https://github.com/user-attachments/assets/ef09886f-7936-4ef7-b01e-4da0008fd047) 
![Screenshot 2024-08-24 195121](https://github.com/user-attachments/assets/219272b8-3938-4b7f-ba78-817e507beee9)
![Screenshot 2024-10-03 093051](https://github.com/user-attachments/assets/d4da51a4-dfe8-4cc1-ae49-745f6e1ae17a) 
![Screenshot 2024-10-03 093106](https://github.com/user-attachments/assets/09c31cf7-e960-4ea5-a858-8807317486b7)
![Screenshot 2024-10-03 093133](https://github.com/user-attachments/assets/b1da1faa-7e71-49be-972a-432029a1e7c3) 
![Screenshot 2024-10-03 093424](https://github.com/user-attachments/assets/b0a9a1e2-6018-475a-95fc-d64980677ee7) 
![Screenshot 2024-10-03 093437](https://github.com/user-attachments/assets/b860f905-2c35-4af3-9df8-38cda70b4406)

## Project Structure

```bash
src/
├── main/
│   ├── java/
│   │   └── com.example.foodfrenzy/
│   │       ├── controller/      # Contains all controllers
│   │       ├── model/           # Contains entity classes
│   │       ├── repository/      # Repository interfaces for database interaction
│   │       └── service/         # Service layer with business logic
│   ├── resources/
│   │   ├── templates/           # Thymeleaf templates for views
│   │   ├── static/              # Static assets (CSS, JavaScript)
│   │   └── application.properties  # Project configuration
│   └── webapp/
│       └── WEB-INF/
│           └── views/           # Additional view files
└── test/                        # Test cases for unit testing
