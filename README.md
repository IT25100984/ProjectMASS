ProjectMASS also known as Project - Medical Appointment Scheduling System. Is a project conducted by Group 006 WD Y1S2 of SLIIT. 
The system consists of users such as patients (make appointments), doctors (accept or change appointment), pharmacists (handle medicine orders) and admins (handle basic requests and review data)
It features a clean MVC (Model-View-Controller) pattern combined with a data access layer of SQL and text files.
The user-interfaces incluse patient dashboard, doctor dashboard, admin dashboard, pharmacist dashboard, feedback form, book appointment, registration, index and login page.

Controller Layer: Acts as the traffic cop. It listens for incoming HTTP requests (like a patient booking a slot), talks to the data layer, and returns the correct view or data.
DAO Layer (Data Access Object): The only layer allowed to talk directly to your database using SQL queries. It isolates the rest of your application from database-specific syntax.
DTO Layer (Data Transfer Object): A lightweight container used to carry data across layers, specially formatted for what the frontend needs to display.

In addition we have services to handle data transfer between text files of reviews, medicine orders, and appointments.
We also make use of session control to prevent unauthorized access from unregistered users.
The system uses Spring Boot annotations and components, such as : 

@Autowired 
What it does: It tells Spring to automatically inject a dependency into our class.
Under the hood: Instead of you manually writing FeedbackDAO dao = new FeedbackDAO();, 
Spring looks inside its application context (its internal registry of managed objects), finds the bean that matches FeedbackDAO, and injects it for us.

@Component 
Is a class-level annotation used to mark a Java class as a Spring-managed bean. 
When we apply this annotation, we are telling the Spring IoC (Inversion of Control) container to automatically detect, instantiate, and 
manage the lifecycle of that class as an object (bean)

@Controller 
Marks a class as a web request handler. It tells Spring that this class contains endpoints that return web views 
(like HTML templates: feedback_form, doctor_feedback).

@Repository 
Used on DAO classes (like FeedbackDAO). It tells Spring this class handles database interactions.
Bonus Viva Point: @Repository automatically enables Data Exception Translation, turning raw, vendor-specific SQL exceptions into Spring's readable DataAccessException hierarchy.

@Service
Used on our business logic and file handling layers (like FeedbackFileService). 
It has no special behavior over @Component, but it structurally designates the class as the place where business rules or file interactions happen.

@GetMapping ("/feedback")
Used exclusively to fetch or render data. It's idempotent, meaning reading the feedback form multiple times won't change the server's state.

@PostMapping("/submitFeedback")
Used to modify state (submitting data to be written into MySQL and your feedback.txt file).

@RequestParam
Extracts query parameters or form field data directly out of the HTTP request. 
For example, @RequestParam("feedbackId") Integer feedbackId grabs the ID from the submitted admin delete form

JdbcTemplate
What it is: Spring's low-level abstraction over raw Java Database Connectivity (JDBC).
Why it's great: It eliminates massive amounts of boilerplate code. Without JdbcTemplate, we would have to manually open database connections, 
create statements, handle result sets, and explicitly close connections in giant try-catch-finally blocks. 
JdbcTemplate handles the connection lifecycle automatically.

GeneratedKeyHolder (The ID Catcher)
What it is: A utility class used to retrieve auto-generated primary keys (like our auto-incremented feedback_id) committed by the database engine during an insert operation.
Why we needed it: Since our file layer (feedback.txt) and our database table both need to reference the exact same unique identifier, 
GeneratedKeyHolder allows us to catch that fresh database ID mid-air so we can immediately write it to your text file log.

