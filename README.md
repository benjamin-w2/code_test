# How to run the application

```
mvn package && docker build --tag code_test .

docker run -t -i -p 8080:8080 code_test
```



