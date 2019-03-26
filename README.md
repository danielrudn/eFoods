# eFoods
This is the final project for EECS 4413 (Building Ecommerce Systems) from Fall 2016 written in Java (with JEE) and vanilla javascript.

## Requirements

#### Core Website (B2C)

eFoods is a simple ecommerce website for food products with the following features:
  - Display products on the homepage and category pages
  - Ability to search for a specific product
  - Shopping cart
  - Checkouts
  - Revisit and view previous purchase order summaries

##### Some screenshots of the website itself:

![home](screenshots/home.png "homepage")

![category](screenshots/category.png "category")

![cart](screenshots/cart.png "cart")

#### Analytics / Ad-hoc functionality

For analytics, the management wants to be able to determine the average time taken for a client to add an item to the cart
as well as the average time between a fresh visit and a checkout in one session. We must provide a way for these two
statistics to be viewed & updated in real time.

Management wants to advertise a certain item when a related item is added to the cart on an ad-hoc basis. As a proof of concept, whenever the item number `1409S413` is added to the cart, cross-sell item number `2002H712`. This functionality should not be part of the webapp since it may be disabled at any time without recompiling.

#### Authentication

Users are redirected to a separate server (York University computer science servers) where they are prompted to log in to their CSE (computer science) account using HTTP Basic authentication. Once logged in, a perl script ([auth.cgi](auth/auth.cgi)) generates a hash and redirects back to eFoods where we validate the hash and determine if the authentication was successful.

The authentication flow is similiar to OAuth since we delegate authentication to a separate entity. The eFoods authentication endpoint only redirects to the other server and verifies received tokens.

