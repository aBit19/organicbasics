# Organic Basics assignment

#### Start the application

Requirements: Java 11, Apache Maven 3.6.0

1) Clone repository, `git clone https://github.com/aBit19/organicbasics.git`
2) and then `cd organicbasics && mvn spring-boot:run`

### Authentication
In order to use most of the endpoints one will need to first register to the application.

This can be done by providing a 'username' and 'password' to `/auth/signup` via a post operation

In case the username already exist the registration will fail with 400 response.

Already registered users can sign in to the application by providing a 'username' and a 'password'
to `/auth/signin` via a post operation.

In case the credentials are valid, the response will contain the authentication token.

This token is to be used in order to access the secure resources of the application. 


Note that all the user information is stored in memory.

### Secure End points
In order to access the end points mentioned in this section, first you will need to 
set the Authorization header to `Bearer 'authentication token'`. 

Here the `'authentication token'` 
refers to the returned token described in the Authentication section.

#### Get commits of a repository

In order to get the details (author, message, and date) of all commits, since 2017, of a specific repository: 

`GET /shopify/repos/commits?repository={repositoryName}`

In case the `repositoryName` is not provided all the commits of the 50 most recent repositories are shown.

#### Get languages of a repository

In order to get the languages of a specific repository: 

`GET /shopify/repos/languages?repository={repositoryName}`

In case the `repositoryName` is not provided all the languages of the 50 most recent repositories are shown.

#### Get the names and URLs of the 50 most recent repositories 

In order to get the name and URLS of most recent repositories: 

`GET shopify/repos/all`


