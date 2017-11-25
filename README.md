CLI prototype for a coupon engine
---
A CLI prototype for evaluation coupon rules driven by [Easy Rules](https://github.com/j-easy/easy-rules)
and [Spring Expression Language](https://docs.spring.io/spring/docs/4.3.12.RELEASE/spring-framework-reference/html/expressions.html). 

### Introduction
This prototype leverages Easy Rules and Spring Expression language for evaluation of coupon rules defined
in JSON files with an easy and expressive way of defining multiple rules. A set of n rules and their options,
like a coupon code, a category of products or a minimum cart value are evaluated based upon a cart defined
in another JSON file. 

The processing is split into two distinct scopes. The first scope is validation scope, in which it is checked
if a coupon is valid in general. Like if the provided code is valid or if a coupon is not yet expired. 
The second scope is application, in which it is checked if a valid coupon is applicable to the customers cart.

#### Prototyped Rules
Rule|Option|Scope|Description
---|---|---|---
Category|Name of category|Application|Rule for evaluating if products are from one category
Exclude|Product SKU|Application|Rule to exclude a product SKU
Expiration|Date (YYYY-mm-dd, i.e. 2018-01-01)|Validation|Rule for an expiration date
MinimumCartValue|Minimum Amount (Integer-based price)|Validation|Rule for a minimum cart value
ValidCode|String containing a coupon code|Validation|Rule for evaluation valid coupon codes

### Build
```
mvn package
```

### Usage
#### General system-independent usage 
```
java -jar target/coupon-prototype-0.0.1-shaded.jar examples/cart.json examples/coupons/coupon-5.json ABC
```

#### U*NIX-based systems launcher
```
./coupon-cli.sh examples/cart.json examples/coupons/coupon-5.json ABC
```

#### Windows-based systems launcher
```
./coupon-cli.bat examples/cart.json examples/coupons/coupon-5.json ABC
```

### CLI & Parameters
java -jar target/coupon-prototype-1.0-SNAPSHOT-shaded.jar *cart* *coupon* *code* *[silent]*

Parameter | Description
--- | ---
cart | JSON file defining a cart
coupon | JSON file defining a coupon
code | A coupon code
silent | Optional boolean for silencing easy rules

### JSON Input Files
#### Carts
```json
{
  "value": 100,
  "items": [
    {
      "sku": "E1000-001",
      "category": "Cheese",
      "price": 50
    },
    {
      "sku": "E1000-010",
      "category": "Cheese",
      "price": 50
    }
  ]
}
```

#### Coupons
```json
{
  "discount": 20,
  "type": "percentage",
  "rules": [
    {
      "rule": "ValidCode",
      "option": "ABC"
    },
    {
      "rule": "MinimumCartValue",
      "option": "35"
    },
    {
      "rule": "Expiration",
      "option": "2018-01-01"
    },
    {
      "rule": "Category",
      "option": "Cheese"
    },
    {
      "rule": "Exclude",
      "option": "E1000-020"
    }
  ],
     "expression": "#ValidCode and #MinimumCartValue and #Expiration and #Category and #Exclude"
}
```