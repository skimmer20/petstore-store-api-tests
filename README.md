# Swagger Petstore Store API Tests

Automated API tests for the **Store endpoints** of the Swagger Petstore using **Java, RestAssured, Maven, and TestNG**.

---

## Project Structure

The project is organized for scalability and maintainability:

- **DTOs** – Data Transfer Objects for request and response mapping
- **Controllers** – Encapsulate API calls
- **Utils & Helpers** – Reusable logic and data generators
- **Tests** – TestNG tests for positive and negative scenarios

---

## How to Run

1. **Clone the repository**

```bash
git clone https://github.com/skimmer20/petstore-store-api-tests
cd <repository-folder>
```
2. **Run tests with Maven**
```bash
mvn clean test
```
3. **Checking the Results**

After the tests finish, open the HTML report in a browser:
```bash
target/extent-report.html
```


The report includes:

- **All tests (Pass / Fail / Skipped)** 
- **Detailed logs for each test**
- **Execution time**
<img width="420" height="288" alt="image" src="https://github.com/user-attachments/assets/821925ff-e983-48e8-a8c3-84c0cef1a779" />

  
