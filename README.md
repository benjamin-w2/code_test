# Running the application

```
mvn package && docker build --tag code_test .

docker run -t -i -p 8080:8080 code_test
```

# Access the database

* Link to the console: http://localhost:8080/h2
* JDBC URL: `jdbc:h2:mem:code_test`
* User Name: `h2_user`
* Password: `h2_user`


