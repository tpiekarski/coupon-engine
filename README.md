Coupon Engine Prototype
---
[![Build Status](https://travis-ci.org/tpiekarski/coupon-engine.svg?branch=master)](https://travis-ci.org/tpiekarski/coupon-engine) 
[![Quality Gate Status](https://sonarcloud.io/api/badges/gate?key=de.delinero.copt:coupon-prototype)](https://sonarcloud.io/dashboard?id=de.delinero.copt%3Acoupon-prototype)
[![Coverage Status](https://coveralls.io/repos/github/tpiekarski/coupon-engine/badge.svg?branch=master)](https://coveralls.io/github/tpiekarski/coupon-engine?branch=master)

*A prototype for evaluating coupon rules driven by [Easy Rules](https://github.com/j-easy/easy-rules)
and [Spring Expression Language](https://docs.spring.io/spring/docs/4.3.12.RELEASE/spring-framework-reference/html/expressions.html)
providing a simple CLI for testing and developing.*

1. [Introduction](#introduction)
2. [Prototyped Rules](#prototyped-rules)
3. [Build](#build)
4. [Dependencies](#dependencies)
5. [Usage](#usage)
6. [JSON Input Examples](#json-input-examples)


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

### Dependencies
\# | Dependency | Version
---|---|---
1.| [Apache Commons IO](https://commons.apache.org/proper/commons-io/) | 2.6
2.| [Apache Maven Shade Plugin](https://maven.apache.org/plugins/maven-shade-plugin/) | 3.1.0
3.| [Maven Coveralls Plugin](https://github.com/trautonen/coveralls-maven-plugin) | 4.3.0
4.| [Easy Rules Core](https://github.com/j-easy/easy-rules) | 3.0.0
5.| [FasterXML Jackson Core](https://github.com/FasterXML/jackson-core) | 2.9.2
6.| [Google Guice Inject](https://github.com/google/guice) | 4.1.0
7.| [Jacoco](https://github.com/jacoco/jacoco) | 0.7.7.201606060606
8.| [jUnit](https://github.com/junit-team/junit4) | 4.12
9.| [Pitest](http://pitest.org/) | 1.2.4
10.| [Spring Expression Language](https://docs.spring.io/spring/docs/4.3.12.RELEASE/spring-framework-reference/html/expressions.html) | 5.0.1

### Usage
#### General system-independent usage 
```
java -jar target/coupon-prototype-0.2.1-shaded.jar examples/cart.json examples/coupon.json
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
java -jar target/coupon-prototype-0.2.1-shaded.jar *cart* *coupon* *silent*

Parameter | Description
--- | ---
cart | JSON file defining a cart
coupon | JSON file defining a coupon
silent | Optional boolean for silencing easy rules (default is true)

### JSON Input Examples
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