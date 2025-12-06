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

>**Week 3** - The project now has a working persistent I/O system. The main task was setting up the Persistence Layer, so our system can now remember what users do. Now, when someone creates a customer account, lists a product, or places an order, all that information is safely saved to files.

>**Week 4** - This week, I have it written down in the planner to, “Write and test the Service Layer classes: PortalManager and InventoryManager. Implement utility interfaces: PaymentProcessor and FarmMarketService.” However, I had already written code for the InventoryManager and PortalManager to help test part of the domain layer. FarmMarketService will still serve as a public front-facing interface, but I will have a concrete class like FarmMarketServiceImpl to implement from it to handle the core logic related to the interface methods. 

>**Week 5** - This week is all about the inventory system. The goal is to have the customer add items to their cart, and checkout. The OrderManager is going to facilitate the transaction using the PaymentProcessor. Based on the payment method, which is just only pickup at this point. Once an order is made, inventory is updated. 

>**Week 6** - Looks, looks, looks. This week is all about building the GUI and achieving an asetheically pleasing layout. The goal is to craft similar to the mockup, but with limited time, it will not look exacly like the mockup, but definitely similar.