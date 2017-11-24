CLI prototype for a coupon engine
---
A CLI prototype for evaluation coupon rules driven by [Easy Rules](https://github.com/j-easy/easy-rules)
and [Spring Expression Language](https://docs.spring.io/spring/docs/4.3.12.RELEASE/spring-framework-reference/html/expressions.html). 

### Build
```
mvn package
```

### Usage
```
java -jar target/coupon-prototype-0.0.1-shaded.jar examples/cart.json examples/coupons/coupon-5.json ABC
```

or using shortcut shell script for launching coupon engine

```
./coupon-cli.sh examples/cart.json examples/coupons/coupon-5.json ABC
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