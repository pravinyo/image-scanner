## Kotlin Library for image processing

[![Publish to Github Packages](https://github.com/pravinyo/image-scanner/actions/workflows/ci-publish-to-github-packages.yaml/badge.svg)](https://github.com/pravinyo/image-scanner/actions/workflows/ci-publish-to-github-packages.yaml)
[![Run all tests](https://github.com/pravinyo/image-scanner/actions/workflows/ci-tests.yaml/badge.svg)](https://github.com/pravinyo/image-scanner/actions/workflows/ci-tests.yaml)

```maven
<dependency>
  <groupId>dev.pravin</groupId>
  <artifactId>image-scanner</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Simple library for doing basic image processing task on image.

- It can apply various filter to image.
- It can do different transformation to the image.
- It can apply different contrast enhancement algorithm to make image better.
- It has image editor class which does the heavy lifting of managing the changes and can undo the task.

## Technical details

- To use this library you need to add additional dependency on opencv library (at least 3.4.X)
- It is implemented using Kotlin language
- It can be used in Android App or any application which runs on JVM