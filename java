# This workflow will upload a Java package to Maven Central when a release is created
# For more information see: https://docs.github.com/en/actions/automating-builds-and-tests/building-and-testing-java#publishing-to-package-registries

name: Upload Java Package to Maven Central

on:
  release:
    types: [published]

permissions:
  contents: read

jobs:
  release-build:
    runs-on: ubuntu-latest

    steps:
      - uses: actions/checkout@v4

      - name: Set up JDK
        uses: actions/setup-java@v3
        with:
          java-version: '11' # or the Java version you are using
          distribution: 'adoptopenjdk' # or another distribution like 'openjdk'

      - name: Build and package the application
        run: |
          # Run Maven to build the Java package
          ./mvnw clean package # Use './mvnw' if you use the Maven Wrapper, otherwise use 'mvn'
      
      - name: Upload build artifacts
        uses: actions/upload-artifact@v4
        with:
          name: java-release
          path: target/*.jar  # Change this if your artifacts are in a different directory or format

  maven-publish:
    runs-on: ubuntu-latest
    needs:
      - release-build
    permissions:
      id-token: write

    environment:
      name: maven-central
      # OPTIONAL: uncomment and update to include your Maven Central project URL:
      # url: https://repo.maven.apache.org/maven2/com/example/yourproject

    steps:
      - name: Retrieve build artifacts
        uses: actions/download-artifact@v4
        with:
          name: java-release
          path: target/

      - name: Publish to Maven Central
        run: |
          # Set up environment variables for Maven Central publishing (adjust for your setup)
          # Ensure you have encrypted your credentials as GitHub Secrets
          mvn deploy:deploy-file \
            -Dfile=target/your-package-name.jar \
            -DrepositoryId=central \
            -Durl=https://repo.maven.apache.org/maven2 \
            -DgroupId=com.example \
            -DartifactId=your-artifact-id \
            -Dversion=${{ github.event.release.tag_name }} \
            -Dpackaging=jar \
            -DgeneratePom=true \
            -Dpush=true
