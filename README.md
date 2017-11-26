CLI prototype for a coupon engine
---
A CLI prototype for evaluation coupon rules driven by [Easy Rules](https://github.com/j-easy/easy-rules)
and [Spring Expression Language](https://docs.spring.io/spring/docs/4.3.12.RELEASE/spring-framework-reference/html/expressions.html). 

### Introduction
This prototype leverages Easy Rules and Spring Expression language for evaluation of coupon rules defined
in JSON files with an easy and expressive way of defining multiple rules. A set of n rules and their options,
like a coupon code, a category of products or a minimum cart value are evaluated based upon a cart defined
in another JSON file.

The processing is divided into two distinct scopes. The first scope is validation scope, in which the coupon
is checked for validity. For example if the provided code is valid or if a coupon is not yet expired.
The second scope is application, in which a valid coupon is checked for application to a cart and its items.

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
java -jar target/coupon-prototype-0.2.0-shaded.jar examples/cart.json examples/coupon.json
```

#### U*NIX-based systems launcher
```
./coupon-cli.sh examples/cart.json examples/coupon.json
```

#### Windows-based systems launcher
```
coupon-cli.bat examples/cart.json examples/coupon.json
```

### CLI & Parameters
java -jar target/coupon-prototype-0.2.0-shaded.jar *cart* *coupon* *silent*

Parameter | Description
--- | ---
cart | JSON file defining a cart
coupon | JSON file defining a coupon
silent | Optional boolean for silencing easy rules (default is true)

### JSON Input Files
#### Carts
```json
{
  "value": 100,
  "code": "ABC",
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
  "validation": {
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
        }
      ],
      "expression": "#ValidCode and #MinimumCartValue and #Expiration"
    },
  "application": {
    "rules": [
      {
        "rule": "Category",
        "option": "Cheese"
      },
      {
        "rule": "Exclude",
        "option": "E1000-020"
      }
    ],
    "expression": "#Category and #Exclude"
  }
}
```