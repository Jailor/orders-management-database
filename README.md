# Orders management databasae

## About :thought_balloon:
The task was to design an order management system for processing client orders for a warehouse.
Relational databases are used to store the products, the clients, and the orders. The
application is designed according to the layered architecture pattern.

## Features :white_check_mark:
The orders management database has the following features:
 - Allows the user to easily choose what they want to edit by offering multiple GUI views: orders, products or clients.
 - Editing and removing is done by simply selecting the entry in the table.
 - Product stock is automatically updated when an order is placed or deleted.
 - Error alerts and confirmation dialogs are displayed to the user.

## Implementation and GUI :computer:

### Notable implementation details
- Scalable generic GUI: There is only one controller for the tables and only one for editing. The fields are dynamically generated using reflection and allows for scalability, adding a new table is very easy.
- Scalable generic DAO: data access class that interacts with the database also uses generics for all CRUD operations, no need to write specific code for each class.
- SQL database: Using a database to ensure data consistency, security and sharing.
- Layered architecture pattern: The packages interact with each other in a layered pattern in order to ensure coherence and cohesion. More details on this are given in the pdf document, as well as diagrams.

### Main menu
<img src="/ss/main_menu.png" width="500" >

### Client Table
<img src="/ss/client_table.png" width="500" >

### Product Table
<img src="/ss/product_table.PNG" width="500" >

### Add View
<img src="/ss/add_view.PNG" width="500" >

### Edit View
<img src="/ss/edit_view.png" width="500" >
