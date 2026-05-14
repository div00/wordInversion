# WordInversion REST API

A SpringBoot REST API that inverses words in a sentence and stores request/response pair in database

Example: '"abc def"' -> '"cba fed"'

#To run locally
- Java 17
- Gradle 

## 1. Extract zip WordInversion.zip 
 ````bash
 unzip WordInversion.zip
 cd WordInversion
````

## 2. Build the Project
 ** Windows (Powershell):**
````powershell
.\gradlew.bat build
````
**Linux\Mac:**
````bash
./gradlew build
````
## 3. Run the application
** Windows (Powershell):**
````powershell
.\gradlew.bat bootRun
````
**Linux\Mac:**
````bash
./gradlew bootRun
````

## Access the API
 The Application will start on **http://locathost:8080**

## API EndPoints
### 1. Inverse Words
 Inverses all words in a sentence and stores the result.

  `URL POST /api/inversions/inverse`

**RequestBody:**
```json
{
    "sentence": "hello world"
}
````

**Response:**

````json
{
    "id": 1,
    "request": "hello world",
    "response": "olleh dirow",
    "createdAt": "2026-05-10T10:30:00",
    "cached": false
}
````
>**Note:** If the same sentence is sent again (case-insensitive), 'cached' will be `true` and the existing record is returned.

### 2. Get All Inversions

Returns all stored request/response pairs.

`URL GET /api/inversions`

### 3. Get Inversion By ID

Return a specific inversion by ID

`URL GET /api/inversions/{id}`

### 4. Get Inversion By Word (in both Request & Reponse)

Return all pairs containing the Word (case -insensitive)

`URL GET /api/inversions/search?word={word}`

### 5. Get Inversion By Word in Request only

Return all pairs containing the Word in request (case -insensitive)

`URL GET /api/inversions/search/request?word={word}`

### 6. Get Inversion By Word in Reponse only

Return all pairs containing the Word in response (case -insensitive)

`URL GET /api/inversions/search/response?word={word}`

### Database

 The application uses H2 in-memory database (no setup required)

### H2 console
You can view it directly in browser

 `URL : http://localhost:8080/h2-console`
 `JDBC URL: 'jdbc:h2:mem:worddb'`
 `Username: sa`
 `Password: (leave empty)`

>Note: Data is reset when the application restarts (in-memoery)

### Configuration

 Configuration is in `src/main/resources/application.properties`

