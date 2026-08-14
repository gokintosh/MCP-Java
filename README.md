# MCP-Java

Learning MCP (Model Context Protocol) development using Java.

This repo is a hands-on walkthrough of building an MCP server in Java from scratch — starting with a minimal SDK sanity check and building up to a real server that communicates over stdio, the same way Claude Desktop talks to local MCP servers.

Following along with the tutorial series from [themcpguy.com](https://themcpguy.com).

## Requirements

- Java 21+
- Maven

## Project structure

```
src/main/java/org/gokul/   Server source code
src/main/resources/            Logback configuration (logs to stderr, keeps stdout clean for MCP protocol messages)
pom.xml                        Project dependencies and build configuration
```

## Dependencies

- [`mcp-core`](https://github.com/modelcontextprotocol) — core MCP protocol implementation
- [`mcp-json-jackson2`](https://github.com/modelcontextprotocol) — Jackson 2-based JSON (de)serialization for MCP messages

## Building

Build a runnable, self-contained jar (via `maven-shade-plugin`):

```bash
mvn package
```

This produces `target/mcp-java-1.0-SNAPSHOT.jar`, bundling the app together with all its dependencies.

## Running

```bash
java -jar target/mcp-java-1.0-SNAPSHOT.jar
```

The server communicates over stdin/stdout using newline-delimited JSON, per the MCP stdio transport spec. Logs are written to stderr so they never interfere with the protocol stream.

## Using with Claude Desktop

Point Claude Desktop's MCP server config at the built jar, e.g.:

```json
{
  "mcpServers": {
    "mcp-java": {
      "command": "java",
      "args": ["-jar", "/absolute/path/to/target/mcp-java-1.0-SNAPSHOT.jar"]
    }
  }
}
```

## Acknowledgements

This project follows the MCP Java tutorial series by [themcpguy.com](https://themcpguy.com). Code structure and concepts are based on that guide as a learning exercise.

## Status

Work in progress — following along as an MCP learning project.
