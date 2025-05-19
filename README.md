Here's a simple `README.md` for your Grails project that demonstrates preserving data binding after reading the request body in a filter:

---

````markdown
# Grails Request Body Handling Demo

This is a sample Grails application that demonstrates **how to read and log the HTTP request body in a filter** without breaking data binding in a controller action.

It uses Spring's `ContentCachingRequestWrapper` to wrap the request, ensuring that the body is cached and readable multiple times — both in filters/interceptors and in the controller where Grails performs automatic data binding.

## 💡 Goal

- Read and log the JSON request body in a filter.
- Ensure Grails' data binding to a domain or command object **still works**.
- Confirm behavior through a Spock test.

---

## 📦 Key Components

- **`RequestLoggingFilter`**: A filter that wraps the request and logs the request body without consuming the input stream.
- **`PersonController`**: A controller that binds incoming JSON to a `Person` command object.
- **`PersonControllerSpec`**: A Spock test that ensures the request body is both logged and correctly bound in the controller.

---

## ✅ Running the Tests

To verify that data binding still works after request body interception:

```bash
./gradlew test
````

You should see output like:

```
[role:Founder, name:Daniella]
Request body: {"role":"Founder","name":"Daniella"}
Binding success: Person(name: Daniella, role: Founder)
BUILD SUCCESSFUL
```

This confirms:

1. The filter accessed the request body and logged it.
2. The controller still received and correctly bound the request to the `Person` object.

---

## 🧪 Sample Test

The test sends a JSON POST request:

```groovy
when:
def response = rest.post("http://localhost:$port/person") {
    contentType('application/json')
    body([name: 'Daniella', role: 'Founder'])
}

then:
response.status == 200
response.json.name == 'Daniella'
response.json.role == 'Founder'
```

---

## 🛠 Stack

* Grails 5.x
* Spock Framework
* Spring Web's `ContentCachingRequestWrapper`

---

## 🧼 Notes

* Avoid using `request.JSON` or accessing the input stream directly in filters unless wrapped properly.
* This approach also works for form-encoded or other content types (with some additional tweaks).

---

## 📁 Structure

```
grails-app/
├── controllers/
│   └── PersonController.groovy
├── filters/
│   └── RequestLoggingFilter.groovy
├── domain/
│   └── Person.groovy
src/test/groovy/
├── PersonControllerSpec.groovy
```
