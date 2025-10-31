# HomesteadHub: Farm-to-Table E-Commerce Application
HomesteadHub is a Java application designed to connect local farmers (producers) directly with community residents (consumers) for purchasing fresh, local goods, eliminating the middleman.

**Project uses a Three-Tier Architecture:**

- Domain Layer: Holds core entities (**Order**, **Customer**, **Product**).


- Service/Logic Layer: Contains the business rules and managers (**OrderManager**, **PriceCalculator**).


- Data Access Layer: Handles data persistence via text files (**FileDataSource**).

**OOD Concepts Demonstrated:**

- Inheritance (**User** --> **Customer/Farmer**).


- Composition (**Customer** owns **Cart**, **Order** owns **Payment**).


- Decoupling with Interfaces (**PaymentProcessor**, **DataRepository**)

**Current Status:**

>**As of Week 2**, the full Domain Layer has been implemented, and all relationships. The manager classes (PortalManager, InventoryManager) are set up with their required Map Collections.
