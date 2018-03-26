
## 使用示例
```java
  PathMappingTree tree = new PathMappingTree();
  tree.addPath("GET|POST /vehicle/{\d+}/info/*");
  tree.addPath("GET /users/search");
  tree.addPath("DELETE /users/s*");
  
  PathElement element = tree.matchPath("/users/sss", "get");
  if (element != null) {
      // 匹配成功
  }
```
