# MyRetail RESTful Service

## Background: 
MyRetail is a rapidly growing company with HQ in Richmond, VA and over 200 stores across the east coast. MyRetail wants to make its internal data available to any number of client devices, from myRetail.com to native mobile apps. 
 
## Scope: 
-	Build an end-to-end Proof-of-Concept for a products API, which will aggregate product data from multiple sources and return it as JSON to the caller.
-	Create a RESTful service that can retrieve product and price details by ID.
 
## Technology Stack used for this POC:

- Java (JDK 8)
- RESTful service using Jersey framework
- REST Client
- Maven 3
- No SQL data store – MongoDB
- Relational database – HSQL DB
- Apache Tomcat 8
- Git

## High Level Design:
For this POC work, I developed two RESTful services.

### 1. MyRetailService:

* This RESTful service 

  * Responds to a product inquiry request for the input product id using HTTP GET and return the product data in JSON or XML format.
  * Read the pricing information from the NoSQL data store - MongoDB.
  * Calls an external API to retrieve the product name and attaches it to the myRetailService’s product data response.
  * REST Endpoint: http://localhost:9080/MyRetailService/products/v1/1
   * Version: v1
   * **Request Type: HTTP GET**
   * Request Input : None
   * Response Output Data: JSON
   * HTTP Header:
         * Accept=application/json 
         * Content-Type=application/json
    * Response Code:
         * HTTP Status Code: 200 – Success
         * HTTP Status Code: 400 – Bad Request
         * HTTP Status Code 500 – Internal Server Error

     **Sample Response data:**  
      **XML:**   
      ```
	  <?xml version="1.0" ?>
	  <productItem>  
	    <prodId>2</prodId>  
	    <prodName>The Small Boat (Blu-ray) Crossscreen)</prodName>
	    <productPrice>  
	      <currencyCode>USD</currencyCode>
	      <value>7650.5</value>  
	    </productPrice>
	  </productItem>
      ``` 
      **JSON:**    
      ```  
      {
        "prodId": "2",  
        "prodName": "The Small Boat (Blu-ray) (Crossscreen)",  
        "productPrice": {  
          "currencyCode": "USD",  
          "value": "7650.5"  
        }  
      }  
      ``` 
* This RESTful service 
   * Accepts an HTTP PUT request to update the pricing information and returns the updated product data in JSON or XML format.
    * REST Endpoint:  http://localhost:9080/MyRetailService/products/v1
    * Version: v1
    * **Request Type: HTTP PUT**    
     * **Sample Request data:**  
        * Request Input: JSON  
	```
       {
         "prodId": "2",  
         "productPrice": {  
           "currencyCode": "USD",  
           "value": "3675.50"  
         }  
       }  
	```
     * **Sample Response data:**  
        * Response data: JSON   - returns the refreshed product instance after update.  
	```
       {
         "prodId": "2",  
         "productPrice": {  
           "currencyCode": "USD",  
           "value": "3675.50"  
         }  
       }  
	```
     * Request Input: JSON  
      * HTTP Header:  
             * Accept=application/json
             * Content-Type=application/json
       * Response Code:
              * HTTP Status Code: 200 – Success
              * HTTP Status Code: 400 – Bad Request
              * HTTP Status Code 500 – Internal Server Error

### 2. External API - ProductDetailServices:

* This RESTful service 

   * Retrieves the product name from the relational database - HSQL DB for the given product id in JSON or XML format using HTTP GET.
   * REST endpoint - http://localhost:9080/ProductDetailServices/productdetail/v1/1.
   * Version: v1
   * **Request Type: HTTP GET**  
   * Request Input : None
   * Response Output Data: JSON
   * HTTP Header:
         * Accept=application/json
         * Content-Type=application/json
    * Response Code:
          * HTTP Status Code: 200 – Success
          * HTTP Status Code: 400 – Bad Request
          * HTTP Status Code 500 – Internal Server Error

 **Sample Response data:**

      **XML:** 
      ``` 
            <?xml version="1.0" ?>
            <productDetailsItem>
                  <prodCategory>TV</prodCategory>
                  <prodId>1</prodId>
                  <prodName>The Big Lebowski (Blu-ray) 
                           (Widescreen)</prodName>
             </productDetailsItem>
      ``` 

      **JSON:**
      ``` 
           {
               "prodCategory": "TV",
               "prodCategory": "TV",
               "prodName": "The Big Lebowski (Blu-ray) (Widescreen)"               
            }
      ``` 
## Modules and Packaging Structure:

There are 3 modules developed for this proto type.

**1. MyJPAUtilities:**

**Purpose:**  
     * The purpose of this module is to act as a JPA layer and is responsible for connecting to the database and return in JSON data structure. This is a rudimentary implementation to prove the concept and to add right abstraction to separate the responsibilities.

**Packages:**  
     * com.retail.common - Contains utility classes required for data operation. 
     * com.retail.common.dataservices –   
             * Database factory and implementation that performs database CRUD                                         operation.  

**Pattern Used:**  
      * Factory Pattern  

**2. ProductDetailServices**  

**Purpose:**  
     * An external API exposed as a RESTful service to return the Product Details information in JSON or XML format.  

**Packages:**  
      * com.retail.product.details.entity   
              - Defines the resources   
      * com.retail.product.details.rest.services   
              - Rest end point and controller                    
      * com.retail.product.details.processor:  
              - Processors to process the request. This includes calling appropriate database methods, do necessary computing and send the product information to the rest controller.  

**3. MyRetailService**  

**Purpose:**    
     * RESTful API that gathers product data from multiple sources and returns it in JSON or XML format.  
     * RESTful API that can update the product pricing details.  

**Packages:**  
     * com.retail.product.details.entity - Defines the resources  
     * com.retail.product.details.rest.services - Rest end point or the controller  
     * com.retail.product.details.processor –  
             * Processors that does process the request.This includes calling appropriate database methods, do necessary computing and send the information to the rest controller.  

**Pattern Used:**  
      * Builder Pattern  

## Project Structure:
          * MyRetailService
                * com.retail.products.entity
                       * ProductItem
                       * ProductPriceItem
                * com.retail.products.processor
                       * ProductProcessor
                * com.retail.products.rest.services
                       * RestClient
                * com.retail.products.rest.services
                       * RetailProductRestController

          * ProductDetailServices
              * com.retail.product.details.entity
                     * ProductDetailsItem
              * com.retail.product.details.processor
                     * ProductDetailsProcessor
              * com.retail.product.details.rest.services
                     * ProductDetailsRestController

          * MyJPAProvider
	        * com.retail.common.dataservices
                       * DataSource
                       * DataSourceFactory
                       * DataSourceType
                       * DBConfiguration
                       * HSQLDBDataSource
                       * MongoDBDataSource
                * com.retail.common
                      * QueryByIdSpec
                      * TableColumnUtils

## Build instructions:

* **MyRetailService**  
 * Build the project using pom.xml and Run As - ‘maven build’ in eclipse IDE.  
 * pom.xml:
        * Dependency jars:  
               * jersey  
               * json  
               * log4j  
               * MyJPAProvider  
        * Create the MyRetailService.war file  

* **ProductDetailServices**  
 * Build the project using pom.xml and Run As - ‘maven build’ in eclipse IDE.  
 * pom.xml:
        * Dependency jars:  
               * jersey  
               * json  
               * log4j  
               * MyJPAProvider  
        * Create the ProductDetailServices.war file  

* **MyJPAProvider**  
 * Build the project using pom.xml and Run As - ‘maven build’ in eclipse IDE.  
 * pom.xml:
        * Dependency jars:  
               * hsqldb.jar    
               * mongodb.jar  
               * log4j  
               * json  
        * Create the MyJPAProvider.jar. This jar file will be added as a dependency jar for MyRetailService and ProductDetailServices projects.  

## Deployment instructions:  
    
   * Stop the Tomcat server.  
   * Deploy the MyJPAProvider.jar file into Tomcat container.     
   * Deploy the ProductDetailServices.war file into Tomcat container.    
   * Deploy the MyRetailService.war file into Tomcat container.  
   * Start the Tomcat server.      

## Database setup instructions:  
   * HSQLDB (Relational Database):   
      *  Run the hsqldb server.  
           * C:\hsqldb\productdb>runServer.bat  
                * The runServer.bat batch file contains the productdb DDL and DML scripts to create the database, create Product and Product_Detail tables and insert records into them. 
      * Run the hsqldb swing application to connect to hsqldb database with interactive sqltool.
           * C:\hsqldb\productdb>runManagerSwing.bat    
	   
	   ![hsqldb-screenshot](https://cloud.githubusercontent.com/assets/22694707/19227048/a347f66a-8e79-11e6-96b1-04431bf3f3f7.png)

 * MongoDB (NoSQL database):     
      *  Run the MongoDB server.  
              *	C:\Program Files\MongoDB\Server\3.2\bin>mongod.exe --config c:\mongodb\mongo.config
      *  Connect to MongoDB 
                     * C:\Program Files\MongoDB\Server\3.2\bin>mongo  
                     * Connect to Product database  
		          * >use productdb  
                     * To insert records into MongoDB                    
                     * db.product.insertMany([{prod_id:1,current_price:1500.50,currency:"USD"},{prod_id:2,current_price:2600.25,currency:"USD"},{prod_id:3,current_price:3800.75,currency:"USD"}])
                     * db.product.find() – to retrieve the records from product collection/table.  

## Class Diagram:  

![myretailservice-class diagram](https://cloud.githubusercontent.com/assets/22694707/19227504/63535fb6-8e80-11e6-85b1-ae1037a0043c.png)



## Sequence Diagram: 
#### MyRetailService - HTTP GET:
![myretailservice - http get](https://cloud.githubusercontent.com/assets/22694707/19258796/04738694-8f41-11e6-8839-418627626294.png)

#### MyRetailService - HTTP PUT:
![myretailservice - http put](https://cloud.githubusercontent.com/assets/22694707/19258794/ffbdb26e-8f40-11e6-9193-9aaf1d118856.png)

#### ProductDetailServices - HTTP GET:
![productdetailservice - http get](https://cloud.githubusercontent.com/assets/22694707/19258795/025e535c-8f41-11e6-847f-c58dad1cac6b.png)
