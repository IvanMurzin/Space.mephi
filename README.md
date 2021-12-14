# space.mephi backend

---
Это api для space.mephi, приложения для просмотра лекций и много-много другого.

---
### Api доступно по ссылке:
https://cyber-mephi.herokuapp.com/

---
# Api документация
Вся авторизация происходит с использованием jwt токена, который стоит передавать через Bearer заголовок

* ### <span style="color:orange;">post</span> /login<br>

request body:

```json
{
  "email": "myemail@domain.name",
  "userName": "User Name",
  "passwordHash": "some hash"
}
```

response:

```json
{
  "token": "jwt token"
}
```

* ### <span style="color:orange;">post</span> /singin<br>

request body:

```json
{
  "email": "myemail@domain.name",
  "passwordHash": "some hash"
}
```

response:

```json
{
  "token": "jwt token"
}
```
