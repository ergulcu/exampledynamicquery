# Example Dynamic Query

Two ways to filter with Spring Data JPA when the client decides which fields to filter on:

- **Query by Example**: fill a probe object with the values to match; empty fields are ignored.
- **Specifications**: combine small predicates, which can also express "one of" and "starts with".

The application starts with 14 demo products (electronic, food, clothes) in an H2 database.

## Run

```bash
./mvnw spring-boot:run      # Java 25; http://localhost:8080
```

## Query by Example

```bash
curl "localhost:8080/productlist/byfilter?type=food"                  # exact match
curl "localhost:8080/productlist/byfilter?type=food&name=apple"        # fields are combined with AND
curl "localhost:8080/productlist/byfilter?name=AN&match=CONTAINS"      # case-insensitive substring
```

Parameters: `productNumber`, `name`, `type`, `match` (`EXACT` by default, or `CONTAINS`).
See `ProductServiceImpl.findByExample`.

## Specifications

```bash
curl "localhost:8080/productlist/byspec?types=food,clothes"
curl "localhost:8080/productlist/byspec?nameContains=an&productNumberPrefix=ba"
```

Parameters: `nameContains`, `types` (comma separated), `productNumberPrefix`. Each predicate in
`ProductSpecifications` returns `Specification.unrestricted()` for an empty input, so they combine
freely with `Specification.allOf`. `%` and `_` in the input are matched literally.

## Tests

```bash
./mvnw verify
```

## License

Copyright 2024 Mehmet ERGULCU. Licensed under the [Apache License 2.0](LICENSE).
