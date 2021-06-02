# Pre-requisites
  - Maven
  - JDK 8
  - JAVA_HOME set as an environment variable

# How to run

Windows:
```sh
$ mvnw spring-boot:run
```

Mac:
```sh
$ ./mvnw spring-boot:run
```

# Code Coverage
![Alt text](jacoco.png?raw=true "Code Coverage")



# Relationship Diagram
![Alt text](http://www.plantuml.com/plantuml/png/JOvD2i8m48NtSufP2xc0b5Bm83AQHocOYOolOAdUtGWIN7xmlP_fGGkkvIO2vy9DJqRCDJRTHVLm_T5voNfKNyi5eSCmmn56v75iLafVrQsWESIJ3UyMSFx7v2tIoryrfRhtCH6PuBdz-000?raw=true "ERD")


# API Usage

Here is a brief API documentation. These APIs follow the [JSend REST Specification](https://github.com/omniti-labs/jsend)

<details>
  <summary>
    Get Categories
  </summary>

  Request:
  ```
  GET http://localhost:8060/api/categories
  ```

  Response:
  ```
    200
    {
        "status": "success",
        "data": [
            {
                "categoryId": 1,
                "name": "Women",
                "parentId": null,
                "children": [
                    {
                        "categoryId": 2,
                        "name": "Clothing",
                        "parentId": 1,
                        "children": [
                            {
                                "categoryId": 3,
                                "name": "Dress",
                                "parentId": 2,
                                "children": [
                                    {
                                        "categoryId": 4,
                                        "name": "Casual Dresses",
                                        "parentId": 3,
                                        "children": []
                                    },
                                    {
                                        "categoryId": 5,
                                        "name": "Party Dresses",
                                        "parentId": 3,
                                        "children": []
                                    }
                                ]
                            }
                        ]
                    },
                    {
                        "categoryId": 6,
                        "name": "T-Shirts",
                        "parentId": 1,
                        "children": [
                            {
                                "categoryId": 7,
                                "name": "Printed T-Shirts",
                                "parentId": 6,
                                "children": []
                            },
                            {
                                "categoryId": 8,
                                "name": "Casual T-Shirts",
                                "parentId": 6,
                                "children": []
                            },
                            {
                                "categoryId": 9,
                                "name": "Plain T-Shirts",
                                "parentId": 6,
                                "children": []
                            }
                        ]
                    }
                ]
            },
            {
                "categoryId": 10,
                "name": "Men",
                "parentId": null,
                "children": [
                    {
                        "categoryId": 11,
                        "name": "Footwear",
                        "parentId": 10,
                        "children": [
                            {
                                "categoryId": 12,
                                "name": "Branded",
                                "parentId": 11,
                                "children": []
                            },
                            {
                                "categoryId": 13,
                                "name": "Non-Branded",
                                "parentId": 11,
                                "children": []
                            }
                        ]
                    },
                    {
                        "categoryId": 14,
                        "name": "T-Shirts",
                        "parentId": 10,
                        "children": [
                            {
                                "categoryId": 15,
                                "name": "Printed T-Shirts",
                                "parentId": 14,
                                "children": []
                            },
                            {
                                "categoryId": 16,
                                "name": "Casual T-Shirts",
                                "parentId": 14,
                                "children": []
                            },
                            {
                                "categoryId": 17,
                                "name": "Plain T-Shirts",
                                "parentId": 14,
                                "children": []
                            }
                        ]
                    },
                    {
                        "categoryId": 18,
                        "name": "Shirts",
                        "parentId": 10,
                        "children": [
                            {
                                "categoryId": 19,
                                "name": "Party Shirts",
                                "parentId": 18,
                                "children": []
                            },
                            {
                                "categoryId": 20,
                                "name": "Casual Shirts",
                                "parentId": 18,
                                "children": []
                            },
                            {
                                "categoryId": 21,
                                "name": "Plain Shirts",
                                "parentId": 18,
                                "children": []
                            }
                        ]
                    }
                ]
            }
        ]
    }
  ```
</details>
<details>
  <summary>
    Add Category
  </summary>

  Request:
  ```
    POST http://localhost:8060/api/categories
    {
      "name" : "Sample Category",
      "parentId" : 1
    }
  ```

  Response:
  ```
    200

    {
        "status": "success",
        "data": {
            "categoryId": 22,
            "name": "Sample",
            "parentId": 1,
            "children": null
        }
    }
  ```
  ```
    400 - The specified parentId does not exist.

    {
      "status" : "fail",
      "message": "Invalid parentId",
      "errorCode": "CAT-ERR-002"
    }
  ```

</details>
<details>
  <summary>
    Update Category
  </summary>

  Request:
  ```
    PATCH http://localhost:8060/api/categories/{categoryId}
    {
      "name" : "Sample5",
      "parentId" : 10
    }
  ```

  Response:
  ```
    200

    {
        "status": "success",
        "data": {
            "message": "Category has been updated successfully."
        }
    }
  ```
  ```
    400 - The specified parentId does not exist.

    {
      "status" : "fail",
      "message": "Invalid parentId",
      "errorCode": "CAT-ERR-002"
    }
  ```
  ```
    400 - If the parentId is equal to the categoryId, this is returned.

    {
      "status" : "fail",
      "message": "Invalid parentId",
      "errorCode": "CAT-ERR-002"
    }
  ```

</details>
<details>
  <summary>
    Delete Category
  </summary>

  Request:
  ```
    DELETE http://localhost:8060/api/categories/{categoryId}
  ```

  Response:
  ```
    200

    {
        "status": "success",
        "data": {
            "message": "Category has been deleted successfully."
        }
    }
  ```

</details>

Swagger documentation is also available at:
```
http://localhost:8060/swagger-ui.html
```

# Front-end Implementation

The front-end implementation is located here: https://github.com/senebii/category-management-frontend
