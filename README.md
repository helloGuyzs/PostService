# PostService API

A robust Spring Boot REST API for managing Posts and Comments with secure session-based authentication, soft deletion support, and rich text formatting capabilities.

## Features

- **CRUD Operations** for Posts and Comments
- **Soft Delete** for Posts (preserves historical data)
- **Rich Text Support** in Comments (bold, italics, hyperlinks)
- **User-Specific Access Control**
- **Session-Based Authentication**
- **Global Exception Handling**
- **Custom Exception Types**
- **DTO Pattern** Implementation
- **Comprehensive API Documentation**
- **Unit & Integration Tests**

## Tech Stack

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- Maven
- H2 Database (default)
- PostgreSQL (optional for production)
- JUnit & Mockito for testing

## Setup Instructions

### Prerequisites

- Java 17 or higher
- Maven 3.8 or higher
- Optional: PostgreSQL (for production)

### Installation

1. Clone the repository:
```bash
git clone https://github.com/helloGuyzs/PostService.git
cd PostService
```

2. Build the project:
```bash
mvn clean install
```

3. Run the application:
```bash
mvn spring-boot:run
```

The API will be available at: `http://localhost:8080/api/`

## Database Schema

### posts Table
| Column    | Type    | Notes                        |
|-----------|---------|-----------------------------|
| id        | INT     | Primary Key, Auto Increment  |
| content   | TEXT    | Post content                 |
| user_id   | INT     | User who created the post    |
| is_active | BOOLEAN | Soft delete flag             |

### comments Table
| Column   | Type | Notes                                              |
|----------|------|---------------------------------------------------|
| id       | INT  | Primary Key, Auto Increment                       |
| post_id  | INT  | Foreign Key → posts.id                            |
| user_id  | INT  | User who made the comment                         |
| comment  | TEXT | Comment content (supports rich text formatting)    |

## API Documentation

### Authentication Endpoints

#### Register User
- **Method**: POST
- **Endpoint**: `/api/auth/register`
- **Request Body**:
```json
{
  "username": "john_doe",
  "password": "secure_password",
  "email": "john@example.com"
}
```
- **Response**: 201 Created

#### Login
- **Method**: POST
- **Endpoint**: `/api/auth/login`
- **Request Body**:
```json
{
  "username": "john_doe",
  "password": "secure_password"
}
```
- **Response**: Sets JSESSIONID cookie for session management

### Post Endpoints

#### Get All Posts
- **Method**: GET
- **Endpoint**: `/api/post/getPost`
- **Response**:
```json
[
  {
    "postId": 1,
    "content": "This is my first post",
    "userId": 100,
    "comments": [
      {
        "userId": 101,
        "comment": "Great post!"
      }
    ]
  }
]
```

#### Get Post by ID
- **Method**: GET
- **Endpoint**: `/api/post/getPost/{postId}`
- **Response**: Single post object with comments

#### Get User Posts
- **Method**: GET
- **Endpoint**: `/api/post/getUserPosts`
- **Response**: Array of user's posts

#### Add Post
- **Method**: POST
- **Endpoint**: `/api/post/addPost`
- **Request Body**:
```json
{
  "content": "This is my first post"
}
```

#### Update Post
- **Method**: POST
- **Endpoint**: `/api/post/updatePost/{postId}`
- **Request Body**:
```json
{
  "content": "Updated post content"
}
```

#### Soft Delete Post
- **Method**: DELETE
- **Endpoint**: `/api/post/softdeletePost/{postId}`
- **Note**: Post remains in DB but marked inactive

#### Delete Post Permanently
- **Method**: DELETE
- **Endpoint**: `/api/post/deletePost/{postId}`

### Comment Endpoints

#### Add Comment
- **Method**: POST
- **Endpoint**: `/api/comment/addComment`
- **Request Body**:
```json
{
  "postId": 1,
  "comment": "Great post! **Bold text** *Italic* [Link](http://example.com)"
}
```

#### Update Comment
- **Method**: POST
- **Endpoint**: `/api/comment/updateComment/{commentId}`
- **Request Body**: Same as Add Comment

#### Get Comment
- **Method**: GET
- **Endpoint**: `/api/comment/getComment/{commentId}`

#### Delete Comment
- **Method**: DELETE
- **Endpoint**: `/api/comment/deleteComment/{commentId}`

## Security Features

### Session Authentication
- Uses Spring Security for session management
- JSESSIONID cookie-based authentication
- Secure session handling and timeout
- CSRF protection enabled

### Access Control
- User-specific access to posts and comments
- Only creators can modify their content
- Authentication required for all write operations

## Exception Handling

### Global Exception Handler
- Centralized error handling
- Consistent error response format
- Appropriate HTTP status codes

### Custom Exceptions
- `BadRequestException`
- `NotFoundException`
- `UnauthorizedException`
- `ForbiddenException`
- `ConflictException`

### Error Response Format
```json
{
  "status": 404,
  "message": "Post not found",
  "timestamp": "2023-08-25T10:00:00Z"
}
```

## Testing

### Running Tests
```bash
mvn test
```

### Test Coverage
- Unit tests for services
- Integration tests for controllers
- Authentication flow testing
- Exception handling scenarios

## Best Practices

### Code Organization
- Clean Architecture principles
- Separation of concerns
- DTO pattern for data transfer
- Repository pattern for data access

### Security
- Session-based authentication
- Password encryption
- CSRF protection
- Input validation

### Data Management
- Soft delete implementation
- Rich text support
- Database indexing
- Transaction management

### Development Workflow
- Version control with meaningful commits
- Code review process
- Comprehensive documentation
- Testing requirements

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.
