
# web-path-matcher

 Simple web path matching utility. Pattern to be matched can use 
  - individual '?' character to represent any character
  - individual '*' character to represent any sequence of characters except '/'
  - path segment with only '*' characters (more than one) to represent any sequence of path elements
 
 Examples

  - PathMatcher.instance("/foo/**") represents paths like /foo or /foo/bar/one
  - PathMatcher.instance("/foo/*.xml") represents paths like /foo/a.xml or /foo/bar.xml
  - PathMatcher.instance("/foo/?.xml") represents paths like /foo/a.xml or /foo/b.xml

 It is helper implementation for implementing http servlet filters and similar implementations.

## Java usage

```java
  PathMatcher matcher = PathMatcher.instance("/foo/**");
  String path = "/foo/bar";
  if(matcher.match(path)) { ... }
```

## Maven usage

```
   <dependency>
      <groupId>com.github.antonsjava</groupId>
      <artifactId>web-path-matcher</artifactId>
      <version>1.0</version>
   </dependency>
```
